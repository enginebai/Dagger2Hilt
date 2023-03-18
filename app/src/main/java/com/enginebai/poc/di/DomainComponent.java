package com.enginebai.poc.di;

import com.enginebai.poc.data.DomainRepository;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@EntryPoint
// ActivityBuilderModule provide @ContributesAndroidInjector(modules) and scope with Activity.
public interface DomainComponent {

//    void inject(MyApplication application);

    // We don't need this injection function because we use the classes in dagger.android package
//    void inject(DomainActivity activity);
//    void inject(DomainFragment fragment);
    /*
     * TODO: inject methods: dialog, view model, intent service
     */
//    void inject(RandomTopicButton customButton);

    /*
     * add some dependencies binding methods: A getA();
     * 1. interface
     * 2. dagger provider itself or injector
     */
    DomainRepository domainRepository();
}
