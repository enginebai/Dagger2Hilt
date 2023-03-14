package com.enginebai.poc.di;

import android.content.Context;

import com.enginebai.poc.MyApplication;
import com.enginebai.poc.ui.singleton.SingletonDetailActivity;
import com.enginebai.poc.ui.singleton.SingletonFragment1;
import com.enginebai.poc.ui.singleton.SingletonFragment2;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
// TODO: Add modules AndroidInjectionModule
@Component(modules = {AppModule.class})
// TODO: extended SingletonComponent interface
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Context context);

        AppComponent build();
    }

    // TODO: add DomainModule
    DomainComponent plus(DomainModule domainModule);

    /*
     * TODO: add some inject methods
     * inject(Fragment)
     * inject(WorkerManager)
     */
    void inject(MyApplication application);
    void inject(SingletonFragment1 fragment);
    void inject(SingletonFragment2 fragment);
    void inject(SingletonDetailActivity activity);

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
