package com.enginebai.core.di

import javax.inject.Qualifier

// Used to annotate the provider function between underlying instance creation
// and entry point for custom component
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InnerProviderQualifier
