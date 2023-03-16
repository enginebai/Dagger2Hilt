package com.enginebai.poc.di;

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
        Builder application(MyApplication application);

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
     * Those extended methods provides the dependencies from the same modules here.
     * Call by:
     *  [O] Application, Activity, Fragment, View
     *  [O] Activity.start() / non-Android class static function
     *  [O] ViewModel
     *  [O] ApplicationDelegate
     *  [O] Object.doSomething() function: AppComponent.userDataHelper() at ActivityCooperator object
     *  [O] FragmentActivity.extension function
     *  FirebaseMessagingService()
     *  SafeJobIntentService
     *  OneSignal.OSRemoteNotificationReceivedHandler
     */
    UserDataHelper userDataHelper();
}
