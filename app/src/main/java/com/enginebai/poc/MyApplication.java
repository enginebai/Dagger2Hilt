package com.enginebai.poc;

import android.app.Application;

import com.enginebai.poc.data.user.UserDataHelper;
import com.enginebai.poc.di.AppComponent;
import com.enginebai.poc.di.AppInjector;
import com.enginebai.poc.di.DaggerAppComponent;
import com.enginebai.poc.di.DomainComponent;
import com.enginebai.poc.di.DomainModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

// TODO: implement HasSingletonComponent
// TODO: implement HasDomainComponent

// MainActivity
// |-- SingleFragmentsActivity
// |---- SingletonFragment 1
// |---- SingletonFragment ..
// |---- SingletonFragment n
// |------ SingletonDetailActivity
// |
// |-- DomainActivity
// |-- DomainFragmentsActivity
// |---- DomainFragment
public class MyApplication extends Application implements HasAndroidInjector {

    // Accessing the interfaces
    public AppComponent appComponent;
    public DomainComponent domainComponent;

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
}