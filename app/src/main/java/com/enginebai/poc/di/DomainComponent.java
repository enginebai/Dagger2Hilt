package com.enginebai.poc.di;

import com.enginebai.core.di.DomainColorModule;
import com.enginebai.core.di.DomainScope;
import com.enginebai.poc.MyApplication;
import com.enginebai.poc.data.domain.DomainRepository;
import com.enginebai.poc.ui.widget.RandomTopicButton;

import dagger.Subcomponent;

@DomainScope
// ActivityBuilderModule provide @ContributesAndroidInjector(modules) and scope with Activity.
@Subcomponent(modules = {
        DomainModule.class,
        ApiModule.class,
        DomainColorModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class
})
public interface DomainComponent {

    void inject(MyApplication application);

    // We don't need this injection function because we use the classes in dagger.android package
//    void inject(DomainActivity activity);
//    void inject(DomainFragment fragment);
    /*
     * TODO: inject methods: dialog, view model, intent service
     */
    void inject(RandomTopicButton customButton);

    /*
     * add some dependencies binding methods: A getA();
     * 1. interface
     * 2. dagger provider itself or injector
     */
    DomainRepository domainRepository();
}
