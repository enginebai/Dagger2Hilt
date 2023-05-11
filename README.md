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