# Dagger2Hilt
This is a POC project to demonstrate how to migrate from Dagger to Hilt, there are several branches/tags served as different purposes that you can checkout:

* `dagger`: The Dagger implementation, it finished the project with Dagger injection, and you can start migration from here.
* `hilt-migration/installin-all-modules-first`: We migrate all modules first with `@InstallIn()` annotation to make sure build successfully at first.
* `hilt-migration/gradually`: We migrate the modules gradually, including modules, android classes, custom components and non-android classes.

# Migration Steps
## Prerequisites
* Convert all java files into kotlin (Application && Component)
* Add hilt gradle dependencies.
* Turning off the `@InstallIn` check temporarily.

## Migrate Application
* Add an new aggregator module to `AppComponent` that includes the same modules from `AppComponent`:
```kotlin
@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        AndroidInjectionModule::class,
        AppModule::class,
        UtilModule::class
    ]
)
interface AppAggregatorModule
```

* For `AppComponent`:
    1. Remove annotate `AppComponent` with `@InstallIn` and `@EntryPoint`.
    2. Extend `AppAggregatorModule`.
    3. Remove `@Component.Builder` interface and `inject(application)`.

```kotlin
// Before
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        UtilModule::class
    ]
)
interface AppComponent : MySingletonComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder
        fun build(): AppComponent
    }
    
    fun inject(application: MyApplication)

    ...
}

// After
@InstallIn(SingletonComponent::class)
@EntryPoint
interface AppComponent : MySingletonComponent, AppAggregatorModule { 
    ...
}
```

* Remove corresponding methods in `MyApplication` that were removed from `AppComponent`:
```kotlin
class MyApplication : Application {
    override fun onCreate() {
        // Remove this
        - appComponent = DaggerAppComponent
        -     .builder()
        -     .application(this)
        -     .build()
        - appComponent.inject(this)
        ...
    }
}
```

* Remove `appComponent` field from `MyApplication` and change the `appComponent()` getter function to return `EntryPoints`:

```kotlin
// Before
class MyApplication : Application {
    private lateinit var appComponent: AppComponent

    fun appComponent(): AppComponent {
        return appComponent
    }
}

// After
class MyApplication : Application {
    // Remove appComponent field

    fun appComponent(): AppComponent {
        return EntryPoints.get(this, AppComponent::class.java)
    }
}
```

* Annotate application with `@HiltAndroidApp` and build the project, we expect the fail build now.
* Fix some `@Provides` functions and dependencies resolving issues. **After this steps, it should be able to build successfully.**
    * Change any subclass that inherits from `AndroidViewModel` to normal `ViewModel` with `@Inject constructor(@ApplicationContext val context: Context)` injection.

* Update `DomainComponent` like `AppComponent` that install in `SingletonComponent` temporarily to make the app work:
    * Add `DomainAggrergatorModule` and extend to it, remove inject functions (the class without injec function might not work)
    * Add `@AliasOf(Singleton::class)` to `DomainScope`.
    * Remove `plus()` from `AppComponent`.

```kotlin
@InstallIn(SingletonComponent::class)
@Module(includes = [
    DomainModule::class,
    ApiModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class
])
interface DomainAggregatorModule

@InstallIn(SingletonComponent::class)
@EntryPoint
interface DomainComponent : DomainAggregatorModule {
    /*
     * add some dependencies binding methods: A getA();
     * 1. interface
     * 2. dagger provider itself or injector
     */
    fun domainRepository(): DomainRepository
}
```

* Finish migrate application with temporary entry point migration, it can build successfully and singleton feature works as usual except for domain component feature, at this moment, we don't annotate any android class with `@AndroidEntryPoint` and still keep `dagger.android` components.

## Migration Android Classes
### Activity, Fragment, Custom View
* Annotate with `@AndroidEntryPoint`.
* Remove the `dagger.android` implementation (if exists):

```diff
-class MainActivity : BaseActivity(), HasAndroidInjector {
+@AndroidEntryPoint
+class MainActivity : BaseActivity() {

-    // For dagger.android
-    @Inject
-    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
-    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
    ...
}

@Module
abstract class ActivityBuilderModule {
-    @ContributesAndroidInjector
-    abstract fun contributeMainActivity(): MainActivity
    ...
}
```

* Remove all injection function from component. (for custom view if exists)
* After migrating **all** activities, fragments, we can remove the `dagger.android` implementation from application and any classes contain `@ContributesAndroidInjector`:

```diff
-class MyApplication : Application(), HasAndroidInjector, HasSingletonComponent {
+class MyApplication : Application(), HasSingletonComponent {

-    // For dagger.android
-    @Inject
-    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
-
-    override fun androidInjector(): AndroidInjector<Any> {
-        return dispatchingAndroidInjector
-    }
    ...
}
```

### ViewModel
* Annotate with `@HiltViewModel` in view model.
* Inheriting from `AndroidViewModel` is no more needed, just inherit `ViewModel()`, we can get `context` by annotated with `@ApplicationContext`.

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : BaseViewModel() {
    ...
}
```

* Remove view model instantiation function, and get injected from `viewModels<T>()` lambda delegation from android extension in activity or fragment.

```diff
-class DomainFragment : BaseFragment(), Injectable {
+@AndroidEntryPoint
+class DomainFragment : BaseFragment() {
-    private lateinit var viewModel: DomainFragmentViewModel
+    private val viewModel: DomainFragmentViewModel by viewModels()

-    viewModel = ViewModelProvider(this, viewModelFactory)[DomainFragmentViewModel::class.j
ava]
    ...
}
```