package com.enginebai.poc.di;

import com.enginebai.poc.data.user.UserDataHelper;
import com.enginebai.poc.util.ColorDefinition;

import java.util.List;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@EntryPoint
public interface AppComponent extends MySingletonComponent {

//    DomainComponent plus(DomainModule domainModule);

    /*
     * TODO: add inject(WorkerManager) function
     */
//    void inject(MyApplication application);
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
    List<ColorDefinition.AppColor> appColors();
}
