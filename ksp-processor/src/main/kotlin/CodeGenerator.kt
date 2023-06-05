import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Nullability
import com.google.devtools.ksp.validate
import java.io.OutputStream

class MySymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return MyCodeGenerator(environment.codeGenerator, environment.logger)
    }
}

fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}

class MyCodeGenerator(
    val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private val visitor = MyVisitor()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getAllFiles()
        val ret = symbols.filter { !it.validate() }.toList()
        symbols.forEach {
            logger.info("qwer file: $it")
            it.accept(visitor, Unit)
        }
        logger.info("qwer retrun $ret")
        return ret
    }

    inner class MyVisitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            logger.info("qwer\tclass: $classDeclaration")
            classDeclaration.primaryConstructor?.parameters?.forEach { param ->
                val parameterName = param.name?.asString()
                val parameterType = param.type.resolve()
                val packageName = parameterType.declaration.packageName.asString()
                val typeName = param.type.toString()

                logger.info("qwer\t\tparam: $packageName, $typeName")
            }
        }

        override fun visitFile(file: KSFile, data: Unit) {
            file.declarations.forEach {
                it.accept(this, Unit)
            }
        }
    }

    inner class BuilderVisitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            classDeclaration.primaryConstructor!!.accept(this, data)
        }

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            val parent = function.parentDeclaration as KSClassDeclaration
            val packageName = parent.containingFile!!.packageName.asString()
            val className = "${parent.simpleName.asString()}Builder"
            val file = codeGenerator.createNewFile(
                Dependencies(true, function.containingFile!!),
                packageName,
                className
            )
            file.appendText("package $packageName\n\n")
            file.appendText("class $className{\n")
            function.parameters.forEach {
                val name = it.name!!.asString()
                val typeName = StringBuilder(
                    it.type.resolve().declaration.qualifiedName?.asString() ?: "<ERROR>"
                )
                val typeArgs = it.type.element!!.typeArguments
                if (it.type.element!!.typeArguments.isNotEmpty()) {
                    typeName.append("<")
                    typeName.append(
                        typeArgs.map {
                            val type = it.type?.resolve()
                            "${it.variance.label} ${type?.declaration?.qualifiedName?.asString() ?: "ERROR"}" +
                                    if (type?.nullability == Nullability.NULLABLE) "?" else ""
                        }.joinToString(", ")
                    )
                    typeName.append(">")
                }
                file.appendText("    private var $name: $typeName? = null\n")
                file.appendText("    internal fun with${name.replaceFirstChar { it.uppercase() }}($name: $typeName): $className {\n")
                file.appendText("        this.$name = $name\n")
                file.appendText("        return this\n")
                file.appendText("    }\n\n")
            }
            file.appendText("    internal fun build(): ${parent.qualifiedName!!.asString()} {\n")
            file.appendText("        return ${parent.qualifiedName!!.asString()}(")
            file.appendText(
                function.parameters.map {
                    "${it.name!!.asString()}!!"
                }.joinToString(", ")
            )
            file.appendText(")\n")
            file.appendText("    }\n")
            file.appendText("}\n")
            file.close()
        }
    }
}