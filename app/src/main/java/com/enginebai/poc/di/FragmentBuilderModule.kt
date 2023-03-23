package com.enginebai.poc.di

import com.enginebai.core.di.MyFragmentScope
import com.enginebai.poc.ui.domain.DomainFragment
import com.enginebai.poc.ui.domain.DomainFragmentUser
import com.enginebai.poc.ui.singleton.SingletonFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FragmentBuilderModuleForSingleton {
    @MyFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSingletonFragment(): SingletonFragment
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class FragmentBuilderModuleForDomain {
    @MyFragmentScope
    @ContributesAndroidInjector(modules = [DomainFragmentModule::class])
    abstract fun contributeDomainFragment(): DomainFragment
}

@Module
@InstallIn(ActivityRetainedComponent::class)
class DomainFragmentModule {
//    @Provides
//    @MyFragmentScope
//    fun provideFragmentArgument(fragment: DomainFragment): DomainFragmentUser {
//        return DomainFragment.getUserData(fragment.arguments)
//    }
}