package com.enginebai.poc.di;

import com.enginebai.core.di.DomainScope;
import com.enginebai.poc.MyApplication;

import dagger.Subcomponent;

@DomainScope
// ActivityBuilderModule provide @ContributesAndroidInjector(modules) and scope with Activity.
@Subcomponent(modules = {
        DomainModule.class,
        ApiModule.class,
        ActivityBuilderModule.class,
        FragmentBuilderModule.class
})
public interface DomainComponent {
    // TODO: add plus() methods, TBD!

    void inject(MyApplication application);

    // We don't need this injection function because we use the classes in dagger.android package
//    void inject(DomainActivity activity);
//    void inject(DomainFragment fragment);
    /*
     * TODO: inject methods
     * dialog
     * view model
     * intent service
     * view
     */

    /*
     * TODO: add some dependencies binding methods: A getA();
     * interface
     * dagger provider itself or injector
     */
}
