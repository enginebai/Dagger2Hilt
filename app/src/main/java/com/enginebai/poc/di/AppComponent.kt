package com.enginebai.poc.di

import android.content.Context
import com.enginebai.core.card.Poker
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.di.koin.AppKoinFacade
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        UtilModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun plus(domainModule: DomainModule): DomainComponent

    // TODO: TODO: add inject(WorkerManager) function
    fun inject(application: MyApplication)

    // We don't need this injection function because we use the classes in dagger.android package
//    fun inject(fragment: SingletonFragment)
//    fun inject(activity: SingletonDetailActivity)

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
    // Provide to dynamic feature
    fun randomNumber(): Int

    fun koinFacade(): AppKoinFacade
}