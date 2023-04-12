This is a POC project to demonstrate how to migrate from Dagger to Hilt, there are several branches/tags served as different purposes that you can checkout:

* `dagger`: The Dagger implementation, it finished the project with Dagger injection, and you can start migration from here.
* `hilt-migration/gradually`: We migrate the modules gradually to `Hilt`, including modules, android classes, custom components and non-android classes.
* `koin-migration/gradually`: We migrate the modules gradually to `Koin`, including modules, android classes, custom components and non-android classes (via `KoinFacade` classes).

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
This class acts as interface between Dagger and Koin, it can bridges the dependency provide function from Dagger to Koin and vice versa.

* Provide instance from Dagger and available in Koin:
```kotlin
@Module
class AppModule {
    @Provides
    fun provideUser(): User {
        return User()
    }
}

class KoinFacade @Inject constructor(
    private val userProvider: Provider<User>
) {
    init {
        startKoin {
            modules(module {
                single { userProvider.get() }
            })
        }
    }
}
```

* Provide instance from Koin and avilable in Dagger:
```kotlin
class KoinFacade @Inject constructor(...) {
    init {
        startKoin {
            modules(module {
                single { User() }
                factory { Product() }
            })
        }
    }

    // Expose here
    val user: User by lazy { koinApp.koin.get() }
    val product: Produce get() = koinApp.koin.get()
}

@Module
class AppModule {
    @Provides
    fun provideUser(koinFacade: KoinFacade): User {
        return koinFacade.user
    }

    @Provides
    fun provideProduct(koinFacade: KoinFacade): Product {
        return koinFacade.product
    }
}
```

> **NOTE**: If you use factory method to create new instance, then make sure you use `get() = koinApp.koin.get()` to call getter every time to get new instance.

## Migration Steps
### For Destination Dependencies

#### Direct Injection
For these types, we can migrate from Dagger to Koin directly, we don't have to keep the provider function in Dagger.

```js
// Dependency Resolving Graph:
Injection -> A -> B -> C
                       *
```

1. Keep the provider function of type in Dagger module.
```kotlin
@Module
class AppColorModule {
    @Provides
    @Singleton
    fun provideAppColor(): List<ColorDefinition.AppColor> {
        return ColorManager.generateColors().map { ColorDefinition.AppColor(it) }
    }
}
```

2. Add Dagger provider of type to `KoinFacade`.
```diff
@DomainScope
class KoinFacade @Inject constructor(
    ...
+   private val appColorsProvider: Provider<List<ColorDefinition.AppColor>>,
    ...
) { ... }
```

3. Add type from the provider to Koin module in `KoinFacade`. **Now you can get type from Koin, and we are still able to get type from Dagger.**
```kotlin
koinApp = startKoin {
    ...
    modules(module {
        appColorsProvider.get()
    })
    ...
}
```

4. Replace the usage of the type with Koin injection.

```diff
-setAppColors((application as MyApplication).appComponent().appColors())
+setAppColors(get<List<ColorDefinition.AppColor>())
```

5. Remove all expose type in Dagger component.

```diff
interface AppComponent : MySingletonComponent {
-   fun appColors(): List<ColorDefinition.AppColor>
}
```
6. Once all usages of type are migrated to Koin, we can remove the provider function of type from Dagger module.

```diff
@Provides
-    @Singleton
-    fun provideAppColor(): List<ColorDefinition.AppColor> {
-        return ColorManager.generateColors().map { ColorDefinition.AppColor(it) }
-    }
```
7. Provide the type directly in Koin module and remove the Dagger provider from `KoinFacade` and its provider function in Dagger module.

```diff

@DomainScope
class KoinFacade @Inject constructor(
    ...
-    private val appColorsProvider: Provider<List<ColorDefinition.AppColor>>,
    ...
) { 
+    single { ColorManager.generateColors().map { ColorDefinition.AppColor(it) } }
}

```

#### Injection in Other Classes
For these types that are the injection in other classes, for example, the following class:
```kotlin
class Repository(
    private val api: ApiService,
    private val database: LocalDatabase
)
```
The two fields `api` and `database` instance belong to this kind of injection.

Most steps are as same as above, except that we don't remove the provide function from Dagger, instead, we have to expose the type from `KoinFacade` and use the field in provide function in Dagger:

```kotlin
class KoinFacade {
    // Expose this type from Koin
    val id: UUID by lazy { koinApp.koin.get() }
}

// Dagger module
@Module
class AppModule {
    // We have to keep this function for Dagger injection for other class
    @Singleton
    @Provides
    fun provideId(koinFacade: SingletonKoinFacade): UUID {
        // Provide from facade
        return koinFacade.id
    }
}

// `name` and `age` are not migrated to Koin, `UserDataHelper` is generated from Dagger, but `id` is generated from Koin and provided by facade from Dagger.
@Singleton
class UserDataHelper @Inject constructor(
    private val context: Context,
    private val id: UUID,
    private val name: String,
    private val age: Int) { ... } 
```

> **NOTE**: Here we might need to create different facade classes for different Dagger scopes, one scope to one facade class to avoid the imcompatible scope definition.

If you don't provide type from Dagger via `KoinFacade`, then you can move the field from constructor injection to field injection, where could be inject from Koin, then you can remove the provide function from Dagger.

```diff
@Singleton
class UserDataHelper @Inject constructor(
    private val context: Context,
-   private val id: UUID,
    private val name: String,
    private val age: Int)
+ : KoinComponent { 
+   private val id: UUID by inject()
}
```

Once we provide all types that injected in constructor in Koin, then we can migrate the type to Koin, for example: 

```kotlin
class Repository(
    private val api: ApiService,
    private val database: LocalDatabase
)
```

We could migrate `Repository` to Koin once both `ApiService` and `LocalDatabase` are provided in Koin.

### For Intermediate Dependencies
```js
// Dependency Resolving Graph:
Injection -> A -> B -> C
             *    *  
```

For `UserDataHelper`, there are some dependencies injected from constructor:

```kotlin
@Singleton
class UserDataHelper @Inject constructor(
    private val context: Context,
    private val id: UUID,
    private val name: String,
    private val age: Int) { ... }
```

We migrate all fields to Koin so that all dependencies could be come from Koin, now we can provide `UserDataHelper` type from Koin. We add type to koin module and remove all provider function of dependencies from Dagger for that type :

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

In some case, the type is still the dependencies of other class (The `B` type of above dependency graph `Injection -> A -> B -> C`), we have to provide type to Dagger if `UserDataHelper` is the dependency of other classes that is provided from Dagger and not migrated to Koin) by exposing type from `KoinFacade`.

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