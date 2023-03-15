package com.enginebai.poc.di

import com.enginebai.core.di.MyFragmentScope
import com.enginebai.poc.ui.domain.DomainFragment
import com.enginebai.poc.ui.singleton.SingletonFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @MyFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSingletonFragment(): SingletonFragment

    @MyFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeDomainFragment(): DomainFragment
}