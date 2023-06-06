import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Nullability
import java.io.File
import java.io.OutputStream

class KoinProviderTestCodeSymbolProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return KoinProviderTestCodeGenerator(environment.codeGenerator, environment.logger)
    }
}

fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}

class KoinProviderTestCodeGenerator(
    val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private val visitor = SourceCodeFileVisitor()

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getAllFiles()
        symbols.forEach {
//            logger.info("qwer file: $it")
            it.accept(visitor, Unit)
        }
        return emptyList()
    }

    inner class SourceCodeFileVisitor : KSVisitorVoid() {
        override fun visitFile(file: KSFile, data: Unit) {
            if (file.fileName == "TestInjection.kt") {
                file.declarations.forEach {
                    it.accept(visitor, Unit)
                }
            }
        }

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            logger.info("qwer\tclass: $classDeclaration")

            val injectedProperties = mutableListOf<String>()
            val injectionPattern = Regex("private\\s+val\\s+\\w+:\\s+(\\w+\\b)(?=\\s+by\\s+inject)", RegexOption.IGNORE_CASE)

            if (classDeclaration.location is FileLocation) {
                val filePath = (classDeclaration.location as FileLocation).filePath
                val sourceCodeFile = File(filePath)
                sourceCodeFile.forEachLine { line ->
                    val matchResult = injectionPattern.find(line)
                    matchResult?.groups?.forEach {
                        logger.info("qwer***$it")
                    }
                }
                classDeclaration.getDeclaredProperties().filter { property ->
                    property.isDelegated()
                }.forEach {
                    val resolvedType = it.type.resolve()
                    logger.info(
                        "qwer\t\t${resolvedType.declaration.qualifiedName?.asString()},"
                    )
                }
            }
        }

        private fun generateTestForType(param: KSValueParameter): String {
            val parameterType = param.type.resolve()
            val packageName = parameterType.declaration.packageName.asString()
            val typeName = param.type.toString()

            val fullName = if (packageName.isNotEmpty()) "$packageName.$typeName" else typeName
            return "assertNotNull(get<$fullName>())"
        }
    }

    inner class SampleBuilderVisitor : KSVisitorVoid() {
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