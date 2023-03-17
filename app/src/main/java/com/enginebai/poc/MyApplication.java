package com.enginebai.poc;

import android.app.Application;

import com.enginebai.core.CoreModule;
import com.enginebai.poc.data.user.UserDataHelper;
import com.enginebai.poc.delegate.CoreApp;
import com.enginebai.poc.di.AppComponent;
import com.enginebai.poc.di.AppInjector;
import com.enginebai.poc.di.DaggerAppComponent;
import com.enginebai.poc.di.DomainComponent;
import com.enginebai.poc.di.DomainModule;
import com.enginebai.poc.di.HasSingletonComponent;
import com.enginebai.poc.di.MySingletonComponent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 *  1. UI Flow:
 *  MainActivity + MainViewModel
 *  |-- SingleFragmentsActivity
 *  |---- SingletonFragment 1
 *  |---- SingletonFragment ..
 *  |---- SingletonFragment n
 *  |------ SingletonDetailActivity
 *  |
 *  |-- DomainActivity
 *  |-- DomainFragmentsActivity
 *  |---- DomainFragment + DomainFragmentViewModel
 *
 *  2. Dependency Graph:
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
 *      -> SingleFragment / SingletonDetailActivity.backgroundColor
 */
public class MyApplication extends Application implements HasAndroidInjector, HasSingletonComponent {

    // Accessing the interfaces
    private AppComponent appComponent;
    private DomainComponent domainComponent;

    @Inject
    UserDataHelper userDataHelper;

    // For dagger.android
    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;
    @Inject
    AppInjector appInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();
        appComponent.inject(this);

        initDelegate();

        // call function from injected filed
        userDataHelper.generateNewUser();

        instantiateDomainComponent();
        appInjector.init(this);
    }

    // onConfigAvailable() method
    public void instantiateDomainComponent() {
        domainComponent = appComponent.plus(new DomainModule());
        domainComponent.inject(this);
    }

    public AppComponent appComponent() {
        return appComponent;
    }

    public DomainComponent domainComponent() {
        return domainComponent;
    }

    public void initDelegate() {
        CoreModule.INSTANCE.init(new CoreApp(this));
    }

    @NonNull
    @Override
    public MySingletonComponent getSingletonComponent() {
        return appComponent;
    }
}