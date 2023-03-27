This is a POC project to demonstrate how to migrate from Dagger to Hilt, there are several branches/tags served as different purposes that you can checkout:

* `dagger`: The Dagger implementation, it finished the project with Dagger injection, and you can start migration from here.
* `hilt-migration/installin-all-modules-first`: We migrate all modules first with `@InstallIn()` annotation to make sure build successfully at first.
* `hilt-migration/gradually`: We migrate the modules gradually, including modules, android classes, custom components and non-android classes.
