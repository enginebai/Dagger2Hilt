package com.enginebai.poc.di;

import com.enginebai.poc.MainActivity;
import com.enginebai.poc.ShopBackApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
// TODO: Add modules AndroidInjectionModule
// TODO: Add some app modules
@Component(modules = {})
// TODO: extended interfaces
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ShopBackApplication application);

        AppComponent build();
    }

    // TODO: add plus() method

    // TODO: add some inject methods
    void inject(MainActivity activity);

    // TODO: add some dependencies binding methods: A getA();
}
