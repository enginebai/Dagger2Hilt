package com.enginebai.poc.ui.singleton

import android.os.Bundle
import android.widget.TextView
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.user.UserDataHelper
import javax.inject.Inject

class SingletonDetailActivity : BaseActivity() {

    @Inject
    lateinit var userDataHelper: UserDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findViewById<TextView>(R.id.textTitle).text = this::class.java.simpleName
        findViewById<TextView>(R.id.textValue).text = userDataHelper.getUser().id
    }
}