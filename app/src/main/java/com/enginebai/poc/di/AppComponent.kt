package com.enginebai.poc.di

import com.enginebai.core.card.Poker
import com.enginebai.core.di.AppColorModule
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.di.koin.SingletonKoinFacade
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        UtilModule::class,
        AppColorModule::class
    ]
)
interface AppComponent : MySingletonComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder
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
    fun userDataHelper(): UserDataHelper
    // Provide to dynamic feature
    fun poker(): Poker
    fun randomNumber(): Int

    fun koinFacade(): SingletonKoinFacade
}