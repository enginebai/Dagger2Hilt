package com.enginebai.poc.di;

import android.content.Context;

import com.enginebai.poc.MyApplication;
import com.enginebai.poc.data.user.UserDataHelper;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        UtilModule.class
})
// TODO: extended SingletonComponent interface
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Context context);

        AppComponent build();
    }

    DomainComponent plus(DomainModule domainModule);

    /*
     * TODO: add inject(WorkerManager) function
     */
    void inject(MyApplication application);
    // We don't need this injection function because we use the classes in dagger.android package
//    void inject(SingletonFragment fragment);
//    void inject(SingletonDetailActivity activity);

    /*
     * TODO: add some dependencies binding methods: A getA();
     * Call by:
     *  [O]Activity
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
    UserDataHelper userDataHelper();
}
