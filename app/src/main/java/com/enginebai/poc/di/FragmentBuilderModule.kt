package com.enginebai.poc.di

import com.enginebai.core.di.MyFragmentScope
import com.enginebai.poc.ui.domain.DomainFragment
import com.enginebai.poc.ui.domain.DomainFragmentUser
import com.enginebai.poc.ui.singleton.SingletonFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FragmentBuilderModule {
//    @MyFragmentScope
//    @ContributesAndroidInjector
//    abstract fun contributeSingletonFragment(): SingletonFragment
//
//    @MyFragmentScope
//    @ContributesAndroidInjector(modules = [DomainFragmentModule::class])
//    abstract fun contributeDomainFragment(): DomainFragment
}

@Module
@InstallIn(ViewModelComponent::class)
class DomainFragmentModule {
//    @Provides
//    fun provideFragmentArgument(fragment: DomainFragment): DomainFragmentUser {
//        return DomainFragment.getUserData(fragment.arguments)
//    }
}