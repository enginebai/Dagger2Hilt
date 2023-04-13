package com.enginebai.poc.di

import com.enginebai.core.di.DomainColorModule
import com.enginebai.core.di.DomainScope
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.di.koin.DomainKoinFacade
import com.enginebai.poc.ui.widget.RandomTopicButton
import com.enginebai.poc.ui.widget.RandomTopicItem
import com.example.feature.data.CardRepository
import com.example.feature.di.CardApiModule
import dagger.Subcomponent

// ActivityBuilderModule provide @ContributesAndroidInjector(modules) and scope with Activity.
@DomainScope
@Subcomponent(
    modules = [
        DomainModule::class,
        ApiModule::class,
        DomainColorModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        CardApiModule::class]
)
interface DomainComponent {
    fun inject(application: MyApplication)

    // We don't need this injection function because we use the classes in dagger.android package
    //    void inject(DomainActivity activity);
    //    void inject(DomainFragment fragment);
    /*
     * TODO: inject methods: dialog, view model, intent service
     */
    fun inject(customButton: RandomTopicButton)
    fun inject(nonAndroidClass: RandomTopicItem)

    /*
     * add some dependencies binding methods: A getA();
     * 1. interface
     * 2. dagger provider itself or injector
     */
    fun koinFacade(): DomainKoinFacade

    // Provide to dynamic feature
}