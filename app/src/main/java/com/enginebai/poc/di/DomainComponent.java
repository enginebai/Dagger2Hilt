package com.enginebai.poc.di;

import com.enginebai.core.di.DomainScope;
import com.enginebai.poc.MyApplication;
import com.enginebai.poc.ui.domain.DomainActivity;

import dagger.Subcomponent;

@DomainScope
// TODO: add ActivityBuilderModule, FragmentBuilderModule modules
// ActivityBuilderModule provide @ContributesAndroidInjector(modules) and scope with Activity.
@Subcomponent(modules = {DomainModule.class})
public interface DomainComponent {
    // TODO: add plus() methods, TBD!

    void inject(MyApplication application);
    void inject(DomainActivity activity);
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
