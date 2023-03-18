package com.enginebai.poc.di

import com.enginebai.core.di.MyActivityScope
import com.enginebai.poc.MainActivity
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.domain.DomainFragmentsActivity
import com.enginebai.poc.ui.singleton.SingletonDetailActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivityBuilderModule {
//
//    @ContributesAndroidInjector
//    abstract fun contributeMainActivity(): MainActivity
//
//    @MyActivityScope
//    @ContributesAndroidInjector
//    abstract fun contributeSingletonFragmentsActivity(): SingletonFragmentsActivity
//
//    @ContributesAndroidInjector
//    abstract fun contributeSingletonDetailActivity(): SingletonDetailActivity
//
//    @ContributesAndroidInjector
//    abstract fun contributeDomainActivity(): DomainActivity
//
//    @MyActivityScope
//    @ContributesAndroidInjector
//    abstract fun contributeDomainFragmentsActivity(): DomainFragmentsActivity

}