package com.enginebai.poc;

import android.app.Application;

import com.enginebai.poc.data.user.UserDataHelper;
import com.enginebai.poc.di.AppComponent;
import com.enginebai.poc.di.DaggerAppComponent;
import com.enginebai.poc.di.DomainComponent;
import com.enginebai.poc.di.DomainModule;

import javax.inject.Inject;

// TODO: implement HasAndroidInjector (dagger.android)
// TODO: implement HasSingletonComponent
// TODO: implement HasDomainComponent
public class MyApplication extends Application {

    // Accessing the interfaces
    public AppComponent appComponent;
    public DomainComponent domainComponent;

    @Inject
    UserDataHelper userDataHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();
        appComponent.inject(this);

        // TODO: call function from injected filed
        userDataHelper.generateNewUser();

        instantiateDomainComponent();
    }

    // onConfigAvailable() method
    public void instantiateDomainComponent() {
        domainComponent = appComponent.plus(new DomainModule());
        domainComponent.inject(this);
    }
}