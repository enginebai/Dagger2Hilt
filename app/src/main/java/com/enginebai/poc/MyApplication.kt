package com.enginebai.poc

import android.app.Application
import com.enginebai.core.CoreModule.init
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.delegate.CoreApp
import com.enginebai.poc.di.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * 1. UI Flow:
 * MainActivity + MainViewModel
 * |-- SingleFragmentsActivity
 * |---- SingletonFragment 1
 * |---- SingletonFragment ..
 * |---- SingletonFragment n
 * |------ SingletonDetailActivity
 * |
 * |-- DomainActivity
 * |-- DomainFragmentsActivity
 * |---- DomainFragment + DomainFragmentViewModel
 *
 * 2. Dependency Graph:
 * MyApplication -> Application -> MainViewModel (AndroidViewModel)
 *
 * MyApplication -> Context -> UserDataHelper
 * Calendar -> UserDataHelper
 * DateFormat -> UserDataHelper
 * UUID -> UserDataHelper
 * Random -> UserDataHelper
 *
 * RGB -> AppColor -> AppComponent.appColor() -> MainActivity.backgroundColor
 * RGB -> SingletonColor -> SingletonComponent interface
 * -> SingleFragment / SingletonDetailActivity.backgroundColor
 */
class MyApplication : Application(), HasAndroidInjector, HasSingletonComponent {
    // Accessing the interfaces
    private lateinit var appComponent: AppComponent
    private lateinit var domainComponent: DomainComponent

    @Inject
    lateinit var userDataHelper: UserDataHelper

    @Inject
    lateinit var appInjector: AppInjector

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
        initDelegate()

        // call function from injected filed
        userDataHelper.generateNewUser()
        instantiateDomainComponent()
        appInjector.init(this)
    }

    // onConfigAvailable() method
    fun instantiateDomainComponent() {
        domainComponent = appComponent.plus(DomainModule())
        domainComponent.inject(this)
    }

    fun appComponent(): AppComponent {
        return appComponent
    }

    fun domainComponent(): DomainComponent {
        return domainComponent
    }

    private fun initDelegate() {
        init(CoreApp(this))
    }

    override val singletonComponent: MySingletonComponent
        get() = appComponent
}