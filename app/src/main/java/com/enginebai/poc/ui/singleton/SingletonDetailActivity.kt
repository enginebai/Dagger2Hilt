package com.enginebai.poc.ui.singleton

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.enginebai.core.CoreModule
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.data.user.WorkoutCoach
import com.enginebai.poc.delegate.CoreApp
import com.enginebai.poc.di.singletonComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SingletonDetailActivity : BaseActivity(), HasAndroidInjector {

    @Inject
    lateinit var userDataHelper: UserDataHelper

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val color = this.singletonComponent.singletonColor.color.toColor()
        findViewById<TextView>(R.id.textTitle).apply {
            text = this::class.java.simpleName
            setTextColor(color)
        }
        findViewById<TextView>(R.id.textValue).apply {
            text = userDataHelper.getUser().toString()
            setTextColor(color)
        }
        findViewById<ViewGroup>(R.id.root).setOnClickListener {
            Toast.makeText(
                it.context,
                "Is 18 years old? ${CoreModule.getAppDelegate().is18YearsOld()}",
                Toast.LENGTH_SHORT
            ).show()
        }
        WorkoutCoach.train(this)
    }
}