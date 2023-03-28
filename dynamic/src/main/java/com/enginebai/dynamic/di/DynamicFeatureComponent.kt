package com.enginebai.dynamic.di

import com.enginebai.core.di.MyActivityScope
import com.enginebai.dynamic.ui.DynamicActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.di.AppComponent
import com.enginebai.poc.di.DomainComponent
import dagger.BindsInstance
import dagger.Component

@MyActivityScope
@Component(
    dependencies = [
        AppComponent::class,
        DomainComponent::class,
    ],
    modules = [
        DynamicFeatureModule::class
    ]
)
interface DynamicFeatureComponent {

    fun inject(activity: DynamicActivity)

    @Component.Factory
    interface Factory {
        fun build(
            @BindsInstance activity: DynamicActivity,
            appComponent: AppComponent,
            domainComponent: DomainComponent,
            dynamicFeatureModule: DynamicFeatureModule
        ): DynamicFeatureComponent
    }

    companion object {
        fun inject(activity: DynamicActivity, module: DynamicFeatureModule) {
            val application = (activity.application as MyApplication)
            DaggerDynamicFeatureComponent.factory()
                .build(
                    activity,
                    application.appComponent(),
                    application.domainComponent(),
                    module
                ).inject(activity)
        }
    }
}