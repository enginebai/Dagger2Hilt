package com.enginebai.poc;

import android.app.Application;
import android.util.Log;

import com.enginebai.poc.data.DomainData;
import com.enginebai.poc.data.DomainRepository;
import com.enginebai.poc.data.DomainType;
import com.enginebai.poc.data.UserDataHelper;
import com.enginebai.poc.di.AppComponent;
import com.enginebai.poc.di.DaggerAppComponent;
import com.enginebai.poc.di.DomainComponent;
import com.enginebai.poc.di.DomainModule;

import java.util.List;

import javax.inject.Inject;

// TODO: implement HasAndroidInjector (dagger.android)
// TODO: implement HasSingletonComponent
// TODO: implement HasDomainComponent
public class MyApplication extends Application {

    // Accessing the interfaces
    public AppComponent appComponent;
    public DomainComponent domainComponent;

    // TODO: Add some inject fields
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

        domainComponent = appComponent.plus(new DomainModule());
        domainComponent.inject(this);
    }

    // TODO: add DomainComponent field
}