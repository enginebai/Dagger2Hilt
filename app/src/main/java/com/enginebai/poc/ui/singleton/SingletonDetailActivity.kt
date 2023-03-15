package com.enginebai.poc.ui.singleton

import android.os.Bundle
import android.widget.TextView
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.user.UserDataHelper
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
        findViewById<TextView>(R.id.textTitle).text = this::class.java.simpleName
        findViewById<TextView>(R.id.textValue).text = userDataHelper.getUser().id
    }
}