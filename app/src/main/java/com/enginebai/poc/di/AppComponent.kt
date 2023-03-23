package com.enginebai.poc.di

import com.enginebai.core.di.AppColorModule
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.user.UserDataHelper
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [
    AndroidInjectionModule::class,
    AppModule::class,
    UtilModule::class,
    AppColorModule::class
])
interface AppAggregatorModule

@InstallIn(SingletonComponent::class)
@EntryPoint
interface AppComponent : MySingletonComponent, AppAggregatorModule {

    fun plus(domainModule: DomainModule): DomainComponent

    // TODO: TODO: add inject(WorkerManager) function
    // We don't need this injection function because we use the classes in dagger.android package

    /*
     * Those extended methods provides the dependencies from the same modules here.
     * Call by:
     *  [O] Application, Activity, Fragment, View
     *  [O] Activity.start() / non-Android class static function
     *  [O] ViewModel
     *  [O] ApplicationDelegate
     *  [O] Object.doSomething() function: AppComponent.userDataHelper() at ActivityCooperator object
     *  [O] FragmentActivity.extension function
     *  FirebaseMessagingService()
     *  SafeJobIntentService
     *  OneSignal.OSRemoteNotificationReceivedHandler
     */
    fun userDataHelper(): UserDataHelper
    fun appColors(): List<ColorDefinition.AppColor>
}