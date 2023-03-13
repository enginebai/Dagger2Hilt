package com.enginebai.poc.di;

import com.enginebai.poc.ShopBackApplication;

import dagger.Subcomponent;

@DomainScope
// TODO: add DomainModule, ActivityBuilderModule, FragmentBuilderModule modules
// ActivityBuilderModule provide @ContributesAndroidInjector(modules) and scope with Activity.
@Subcomponent(modules = {})
public interface DomainComponent {
    // TODO: add plus() methods, TBD!

    void inject(ShopBackApplication application);
    /*
     * TODO: inject methods
     * fragment
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
