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
            })
        }
    }

    // Expose here
    val user: User by lazy { koinApp.koin.get() }
}

@Module
class AppModule {
    @Provides
    fun provideUser(koinFacade: KoinFacade): User {
        return koinFacade.user
    }
}
```

## Migration Steps
### For Destination Dependencies

#### Direct Injection

```js
// Dependency Resolving Graph:
injection -> A -> B -> C
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

3. Add type from the provide in Step 2. to Koin module in `KoinFacade`. **Now you can get type from Koin, and we are still able to get type from Dagger.**
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
* Most steps are as same as above, except that we don't remove the provide function from Dagger, instead, we have to expose the type from `KoinFacade` and use the field in provide function in Dagger:

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

### For Intermediate Dependencies
```js
// Dependency Resolving Graph:
injection -> A -> B -> C
             *    *  
```