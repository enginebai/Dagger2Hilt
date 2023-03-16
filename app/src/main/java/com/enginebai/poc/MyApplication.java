package com.enginebai.poc;

import android.app.Application;

import androidx.annotation.NonNull;

import com.enginebai.core.CoreModule;
import com.enginebai.poc.data.user.UserDataHelper;
import com.enginebai.poc.delegate.CoreApp;
import com.enginebai.poc.di.AppComponent;
import com.enginebai.poc.di.AppInjector;
import com.enginebai.poc.di.DaggerAppComponent;
import com.enginebai.poc.di.DomainComponent;
import com.enginebai.poc.di.DomainModule;
import com.enginebai.poc.di.HasSingletonComponent;
import com.enginebai.poc.di.SingletonComponent;

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

    public AppComponent component() {
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
    public SingletonComponent getSingletonComponent() {
        return appComponent;
    }
}