package com.enginebai.poc

import android.app.Application
import com.enginebai.core.CoreModule.init
import com.enginebai.core.Initializer
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.delegate.CoreApp
import com.enginebai.poc.di.*
import com.enginebai.poc.di.koin.DomainInstance
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
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

    private val scopeTestModule = module {
        single { DomainInstance() }
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
        appInjector.init(this)
        initDynamicFeatureModule()
        instantiateDomainComponent()
    }

    // onConfigAvailable() method
    fun instantiateDomainComponent() {
        domainComponent = appComponent.plus(DomainModule())
        domainComponent.inject(this)
        domainComponent.koinFacade()
        unloadKoinModules(scopeTestModule)
        loadKoinModules(scopeTestModule)
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

    private fun initDynamicFeatureModule() {
        val implementationName = "com.enginebai.dynamic.DynamicFeatureInitializer"
        val initializer: Initializer = Class.forName(implementationName).newInstance() as Initializer
        initializer.initialize(this)
    }
}