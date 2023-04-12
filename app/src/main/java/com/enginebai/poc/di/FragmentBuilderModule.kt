package com.enginebai.poc.di

import com.enginebai.core.di.MyFragmentScope
import com.enginebai.poc.ui.domain.DomainFragment
import com.enginebai.poc.ui.domain.DomainFragmentUser
import com.enginebai.poc.ui.singleton.SingletonFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @MyFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSingletonFragment(): SingletonFragment

    @MyFragmentScope
    @ContributesAndroidInjector(modules = [DomainFragmentModule::class])
    abstract fun contributeDomainFragment(): DomainFragment
}

@Module
class DomainFragmentModule {
}