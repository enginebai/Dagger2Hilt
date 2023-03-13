package com.enginebai.poc.di;

import com.enginebai.poc.ShopBackApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
// TODO: Add modules AndroidInjectionModule
// TODO: Add app module
@Component(modules = {})
// TODO: extended interfaces
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(ShopBackApplication application);

        AppComponent build();
    }

    // TODO: add DomainModule
    DomainComponent plus();

    /*
     * TODO: add some inject methods
     * inject(Fragment)
     * inject(WorkerManager)
     */

    /*
     * TODO: add some dependencies binding methods: A getA();
     * Call by:
     *  Activity
     *  Activity.start() / non-Android class static function
     *  Fragment
     *  BaseMvvmFragment + BaseMvvmDialogFragment
     *  ViewModel
     *  ApplicationDelegate
     *  Object.doSomething() function
     *  FirebaseMessagingService()
     *  SafeJobIntentService
     *  OneSignal.OSRemoteNotificationReceivedHandler
     *  Application
     *  FragmentActivity.extension function
     *  View
     */
}
