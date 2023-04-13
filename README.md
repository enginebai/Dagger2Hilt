This is a POC project to demonstrate how to migrate from Dagger to Hilt/Koin, there are several branches/tags served as different purposes that you can checkout:

* `dagger`: The Dagger implementation, it finished the project with Dagger injection, and you can start migration from here.
* `hilt-migration/gradually`: We migrate the modules to Hilt gradually to `Hilt`, including modules, android classes, custom components and non-android classes.
* `koin-migration/gradually`: We migrate the modules gradually to `Koin` to Hilt, including modules, android classes, custom components and non-android classes (via `KoinFacade` classes).

# Koin Migration
To achieve gradulally migration, we utilitize the `KoinFacade` for us to bridge the type provider functions between Dagger and Koin, so that we can keep either the Dagger provider function or instance injection from Dagger, and move the instance creation to Koin or change the instance injection from Koin gradually.

## Specifications
### `SingletonFragmentsActivity`
1. Singleton color are applied on TextViews in `SingletonFragment` 
2. `SingleFragment` displays the singleton user.
3. The color and the user stay the same when you exit and re-enter the screen again.
4. Click the next button to navigate to `SingletonDetailActivity`.
5. Click `SingleFragment` to navigate to next fragment, and the color and the user stay the same, and then to `SingletonDetailActivity`.

### `SingleDetailActivity`
1. Display the singleton color on the background of `SingletonDetailActivity`, and the color should be the same.
2. Display the singleton user in `SingletonDetailActivity`, and the user should be the same.
3. Click the background to check if the age of the singleton user >= 18 in `SingletonDetailActivity`.

### `DomainActivity`
1. Domain color are applied on the background of `DomainDetailActivity`.
2. `DomainDetailActivity` displays the DSAs, and click to enter the `DomainFragmentsActivity`.
3. There is a button (with DSA name as text) to add new DSA.

### `DomainFragmentsActivity`
1. Display the DSAs on TextView and it's as same as the DSAs in `DomainActivity`.
2. Click the next button to append new DSA and set the TextView to display the latest DSAs.
3. The DSAs in `DomainActivity` should be the same as the DSAs in `DomainFragmentsActivity` when going back.

### Change Domain
1. Refresh all the dependencies annotated in `@DomainScope`. (DSA, color, cards, user)

### DSA
1. Append the dsa displayed on the Button.

### Magic
1. Refresh all the colors.

### Card Feature
1. Enter the `CardActivity`, and display card list.

### Dynamic Feature
1. Display card linked list on activity and fragment.
2. Manipulate the linked list to add new card to head and rear of list.
3. Re-create the card linked list when the activity is re-created.

## Facade Class
This class acts as bridge between Dagger and Koin, it can provide the dependency from Dagger to Koin and vice versa.

* Provide instance from Dagger and injectable in Koin:
```kotlin
// Dagger
@Module
class AppModule {
    @Provides
    fun provideUser(): User {
        return User()
    }
}

class KoinFacade @Inject constructor(
    // This is from Dagger
    private val userProvider: Provider<User>
) {
    init {
        // Koin
        startKoin {
            modules(module {
                // Bridge from Dagger to Koin
                single { userProvider.get() }
            })
        }
    }
}

// Now you can use Koin injection
class MyActivity : Activity {
    // Injected from Koin   
    private val user by inject<User>()
    ...
}
```

* Provide instance from Koin and injectable in Dagger:
```kotlin
// We have to make sure the facade object is created by dagger.
class KoinFacade @Inject constructor(...) {
    init {
        startKoin {
            modules(module {
                // Available in Koin
                factory { Product() }
            })
        }
    }

    // Expose here from Koin to Dagger
    val product: Produce get() = koinApp.koin.get()
}

// Dagger
@Component
class AppComponent {
    // Expose facade object
    @Singleton
    fun koinFacade(): KoinFacade
}

@Module
class AppModule {
    @Provides
    fun provideProduct(koinFacade: KoinFacade): Product {
        return koinFacade.product
    }
}

class MyActivity : Activity {
    // Dagger
    @Inject
    lateinit var product: Product
}
```

> **NOTE**: If you use factory method to create new instance, then make sure you use `get() = koinApp.koin.get()` to call getter every time to get new instance.

## Migration Steps
There are different type of injection and we shall migrate in the following order:

1. The type is the destination component of dependency graph, and is not the dependency of other class (destination component):
```js
// Dependency Resolving Path:
Injection -> A
             *
```

2. The type is the destination component of dependency graph, and is the dependency of other class:
```js
// Dependency Resolving Path:
Injection -> A -> B -> C
                       *
```

3. The type is the intermediate node of dependency graph, and is the dependency of other class:
```js
// Dependency Resolving Path:
Injection -> A -> B -> C
                  *  
```

4. The type is the intermediate node of dependency graph, and is not the dependency of other class:
```js
// Dependency Resolving Path:
Injection -> A -> B -> C
             *      
```

### For Destination Component & Not Dependency of Other Class
```js
// Dependency Resolving Path:
Injection -> A
             *
```
For these types, we can migrate from Dagger to Koin directly, we don't have to keep the provider function in Dagger.

1. Keep the provider function of type in Dagger module.
```kotlin
@Module
class UtilModule {
    @Provides
    @Singleton
    fun provideDateFormat(): DateFormat {
        return SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    }
}
```

2. Add Dagger provider of type to `KoinFacade`.
```diff
class KoinFacade @Inject constructor(
    ...
+   private val dateFormatProvider: Provider<DateFormat>,
    ...
) { ... }
```

3. Add type from the provider to Koin module in `KoinFacade`. **Now you can get type from Koin, and we are still able to get type from Dagger.**
```diff
class KoinFacade @Inject constructor(...) {
    koinApp = startKoin {
        ...
        modules(module {
+           dateFormatProvider.get()
        })
        ...
    }
}
```

4. Replace the usage of the type with Koin injection.

```diff
class MyActivity {
-   @Inject
-   lateinit var dateFormat: DateFormat
+   private val dateFormat by inject<DateFormat>()
    ...
}
```

6. Once all usages of type are migrated to Koin, we can remove the provider function of type from Dagger module and expose type in Dagger component (if there is).

```diff
interface AppComponent : ... {
-   fun dateFormat(): DateFormat
}

@Provides
class UtilModule {
-   @Provides
-   @Singleton
-   fun provideDateFormat(): DateFormat {
-       return SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
-   }
}
```

7. Provide the type directly in Koin module (`KoinFacade`) and remove the Dagger provider field from `KoinFacade` and its provider function in Dagger module.

```diff

@DomainScope
class KoinFacade @Inject constructor(
    ...
-   private val dateFormatProvider: Provider<DateFormat>,
    ...
) { 

    koinApp = startKoin {
        ...
        modules(module {
+           single<DateFormat> { SimpleDateFormat("yyyy/MM/dd HH:mm:ss") }
        })
        ...
    }
}
```

### For Destination Component & Is Dependency of Other Class
```js
// Dependency Resolving Path:
Injection -> A -> B -> C
                       *
```

These types are the detination node of dependency graph and it is the dependency of other class, for example, the following class:
```kotlin
class DateFormatter @Inject constructor(
    val dateFormat: DateFormat,
    val calendar: Calendar
)

@Module
class UtilModule {
    @Provides
    fun provideDateFormat(): DateFormat {
        return SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    }

    @Provides
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }
}
```
The two fields `calendar` and `dateFormat` belong to this kind of injection. They have no dependency on other class, but they are the dependencies of other class.

The migration steps are almost the same as those above, except for that we still have keep the provide function from Dagger, expose the type from `KoinFacade` and use the field in the original provider function in Dagger:

```kotlin
class KoinFacade {
    // Expose this type from Koin
    val dateFormat: DateFormat by lazy { koinApp.koin.get() }
}

// Dagger module
@Module
class UtilModule {
    // We have to keep this function for Dagger injection for other class
    @Provides
    fun provideDateFormat(koinFacade: KoinFacade): DateFormat {
        // Provide from facade
        return koinFacade.dateFormat
    }
}

// The field `calendar` is not migrated to Koin, `DateFormatter` is generated from Dagger, but `dateFormat` is generated from Koin and provided by facade from Dagger.
class DateFormatter @Inject constructor(
    val dateFormat: DateFormat,
    val calendar: Calendar
)
```

> **NOTE**: Here we might need to create different facade classes for different Dagger scopes, one scope to one facade class to avoid the imcompatible scope definition.

> If you don't want to provide type from Dagger via `KoinFacade`, then you can move the field from constructor injection to field injection where could be injected from Koin, then you can remove the provide function from Dagger.

```diff
class DateFormatter @Inject constructor(
-   val dateFormat: DateFormat,
    val calendar: Calendar)
+ : KoinComponent { 
+   private val dataFormat: DateFormat by inject()
}
```

### For Intermediate Dependencies & Is Dependency of Other Class
After migrating all destination components (whether it is or is not the dependency of other class), then we can start migrating the intermediate dependencies. 

```js
// Dependency Resolving Path:
Injection -> A -> B -> C
                  *  
```

After migrating `C`, we can start migrating `B`, and we have to migrate `B` first and later migrate `A` and the injection in order.

For `UserDataHelper`, there are some dependencies injected from constructor:

```kotlin
@Singleton
class UserRepository @Inject constructor(
    private val context: Context,
    private val userApi: UserApiService,
    private val userDatabase: UserDatabase,
    private val cacheManager: CacheManager) { 
    ...
}
```

We migrate all fields to Koin so that all dependencies could be injected from Koin, now we can provide `UserDataHelper` type from Koin. We add type to koin module and remove all provider function of dependencies from Dagger for that type :

```diff

@Module
class UtilModule {
-   @Singleton
-   @Provides
-   fun provideId(koinFacade: AppKoinFacade): UUID {
-       return koinFacade.id
-   }
}

class KoinFacade {
    private fun appModule() = module {
        single { pokerProvider.get() }
+       singleOf(::UserDataHelper)
    }
}
```

In some case, the type is still the dependencies of other class (The `B` type of above dependency graph `Injection -> A -> B -> C`), we have to provide type to Dagger if `UserDataHelper` is the dependency of other class that is provided from Dagger and not migrated to Koin) by exposing type from `KoinFacade`.

```diff
class KoinFacade {
+   val userDataHelper: UserDataHelper by lazy { koinApp.koin.get() }

    private fun appModule() = module {
        single { pokerProvider.get() }
+       singleOf(::UserDataHelper)
    }
}

@Module
class AppModule {
    @Provides
    @Singleton
+    fun provideUserDataHelper(koinFacade: AppKoinFacade): UserDataHelper {
+        return koinFacade.userDataHelper
    }
}
```

> 1. It's fine to keep `@Inject constructor` annotation after migrating the type to Koin?!
> 2. If the type is generated by Dagger automatically, then we have to add provider function to Dagger module.

### Migrate Complex Injection
```kotlin
ComplexInjection
    -> private val username: String,
        -> Calendar
        -> DateFormat
    -> private val poker: Poker,
    -> private val userDataHelper: UserDataHelper, 
        -> Context
        -> UUID
        -> String
        -> Int
    -> private val domainRepository: DomainRepository,
        -> DomainApi
    -> private val cardRepository: CardRepository,
        -> CardApi
```

We have to migrate all intermediate dependencies (all the fields in constructor) to provide type in Koin and expose to Dagger via `KoinFacade`. Then we can provide `ComplexInjection` in Koin and expose to Dagger provider function. Now we can remove all provider functions from Dagger and expose fields from `KoinFacade` that injected in `ComplexInjection` constructor.

### For ViewModel
* Once we migrate all dependencies injected from ViewModel constructor, we can provide the ViewModel in Koin and injected by Koin `viewModel()` delegation function and remove the ViewModel factory and instantiation in Android or Fragment.
* For fragment argument injection: We can use `SavedStateHandle` to help (as same step as we implement in Hilt, [source](https://proandroiddev.com/passing-safe-args-to-your-viewmodel-with-hilt-366762ff3f57)):

```diff
class DomainFragmentViewModel @Inject constructor(
    private val domainRepository: DomainRepository,
-    domainFragmentUser: DomainFragmentUser,
+    state: SavedStateHandle,
     ...
) {
+   val domainFragmentUser: DomainFragmentUser? = state.get<DomainFragmentUser>(KEY_DOMAIN_USER)
    ...
)
```

Then provide in Koin and injected as view model usual injection.

> This feature is supported since [`Koin 3.3.0`](https://insert-koin.io/docs/reference/koin-android/viewmodel#savedstatehandle-injection-330).

### Remove Facade Classes
After migrating all types to Koin and won't injected from Dagger

### Clean-up Dagger Implementation
, our codebase will be structured as shown below:

* All modules contain no any provider functions, including from `KoinFacade`.
```diff
@Module
class AppModule {
-   @Providers
-   fun provideUser(): User {
-       ...
-   }
-
-   @Providers
-   fun provideProduct(facade: KoinFacade): Product {
-       ...
-   }
}
```

* No any expose types in component except for `KoinFacade`.
```diff
@Component
interface AppComponent {
-   fun user(): User
}
```


We are able to remove all Dagger implementation: