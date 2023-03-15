package com.enginebai.poc.di

import com.enginebai.core.di.MyActivityScope
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.domain.DomainFragmentBuilderModule
import com.enginebai.poc.ui.domain.DomainFragmentsActivity
import com.enginebai.poc.ui.singleton.SingletonDetailActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentBuilderModule
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @MyActivityScope
    @ContributesAndroidInjector(
        modules = [SingletonFragmentBuilderModule::class]
    )
    abstract fun contributeSingletonFragmentsActivity(): SingletonFragmentsActivity

    @ContributesAndroidInjector
    abstract fun contributeSingletonDetailActivity(): SingletonDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeDomainActivity(): DomainActivity

    @MyActivityScope
    @ContributesAndroidInjector(
        modules = [DomainFragmentBuilderModule::class]
    )
    abstract fun contributeDomainFragmentsActivity(): DomainFragmentsActivity

}