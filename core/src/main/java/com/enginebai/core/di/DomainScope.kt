package com.enginebai.core.di

import dagger.hilt.migration.AliasOf
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention(AnnotationRetention.RUNTIME)
// TODO: Remove this alias after migrating the domain component.
@AliasOf(Singleton::class)
annotation class DomainScope
