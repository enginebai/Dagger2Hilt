package com.enginebai.poc.di

import com.enginebai.core.di.MyActivityScope
import com.enginebai.poc.MainActivity
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.domain.DomainFragmentsActivity
import com.enginebai.poc.ui.singleton.SingletonDetailActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import com.example.feature.ui.CardActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MyActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSingletonFragmentsActivity(): SingletonFragmentsActivity

    @ContributesAndroidInjector
    abstract fun contributeSingletonDetailActivity(): SingletonDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeDomainActivity(): DomainActivity

    @MyActivityScope
    @ContributesAndroidInjector
    abstract fun contributeDomainFragmentsActivity(): DomainFragmentsActivity

    @MyActivityScope
    @ContributesAndroidInjector
    abstract fun contributeCardActivity(): CardActivity

}