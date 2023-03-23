package com.enginebai.poc.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.enginebai.core.di.HasCustomInjector
import com.enginebai.core.di.Injectable
import com.enginebai.poc.MyApplication
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInjector @Inject constructor() {

    fun init(app: MyApplication) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleInjection(activity)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }

    private fun handleInjection(activity: Activity) {
        if (activity is HasAndroidInjector) {
            performInject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(object :
                    FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentAttached(
                        fm: FragmentManager,
                        f: Fragment,
                        context: Context
                    ) {
                        super.onFragmentAttached(fm, f, context)
                        if (f is Injectable) {
                            performInject(f)
                        }
                    }
                }, true)
        }
    }

    private fun performInject(obj: Any) {
        if (obj is HasCustomInjector) {
            obj.performInject()
        } else {
            when (obj) {
                is Fragment -> AndroidSupportInjection.inject(obj)
            }
        }
    }
}