package com.enginebai.dynamic.di

import com.enginebai.dynamic.ui.DynamicActivity
import com.enginebai.dynamic.ui.DynamicFragment
import com.enginebai.poc.MyApplication
import com.enginebai.poc.di.AppComponent
import com.enginebai.poc.di.custom.DomainCustomComponentEntryPoint
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [
        AppComponent::class,
        DomainCustomComponentEntryPoint::class
    ],
    modules = [
        DynamicFeatureModule::class
    ]
)
interface DynamicFeatureComponent {

    fun inject(activity: DynamicActivity)
    fun inject(fragment: DynamicFragment)

    @Component.Factory
    interface Factory {
        fun build(
            @BindsInstance activity: DynamicActivity,
            appComponent: AppComponent,
            domainComponent: DomainCustomComponentEntryPoint,
            dynamicFeatureModule: DynamicFeatureModule
        ): DynamicFeatureComponent
    }

    companion object {
        fun createComponent(activity: DynamicActivity, module: DynamicFeatureModule): DynamicFeatureComponent {
            val application = (activity.application as MyApplication)
            return DaggerDynamicFeatureComponent.factory()
                .build(
                    activity,
                    application.appComponent(),
                    application.domainComponent(),
                    module
                )
        }
    }
}