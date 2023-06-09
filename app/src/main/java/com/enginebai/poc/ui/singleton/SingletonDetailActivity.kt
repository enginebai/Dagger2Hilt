package com.enginebai.poc.ui.singleton

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.enginebai.core.CoreModule
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.data.user.TimeMachine
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findViewById<TextView>(R.id.textTitle).text = this::class.java.simpleName
        val textValue = findViewById<TextView>(R.id.textValue)
        textValue.text = userDataHelper.getUser().toString()
        val color = this.singletonComponent.singletonColor.color.toColor()
        findViewById<ViewGroup>(R.id.root).apply {
            setBackgroundColor(color)
            setOnClickListener {
                textValue.text = "Is 18 years old? ${CoreModule.getAppDelegate().is18YearsOld()}"
            }
        }
    }
}