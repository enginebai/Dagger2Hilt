package com.enginebai.poc;

import android.app.Application;

import com.enginebai.poc.data.UserDataHelper;
import com.enginebai.poc.di.AppComponent;
import com.enginebai.poc.di.DaggerAppComponent;

import javax.inject.Inject;

// TODO: implement HasAndroidInjector (dagger.android)
// TODO: implement HasSingletonComponent
// TODO: implement HasDomainComponent
public class MyApplication extends Application {

    // Accessing the interfaces
    public AppComponent appComponent;

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
    }

    // TODO: add DomainComponent field
}