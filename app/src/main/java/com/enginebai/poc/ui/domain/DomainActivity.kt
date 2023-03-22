package com.enginebai.poc.ui.domain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.DomainRepository
import com.enginebai.core.util.ColorDefinition
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DomainActivity : BaseActivity(), HasAndroidInjector {

    companion object {
        fun start(context: Context) {
            (context.applicationContext as MyApplication).appComponent().userDataHelper().getUser().age = 0
            context.startActivity(Intent(context, DomainActivity::class.java))
        }
    }

    @Inject
    lateinit var domainRepository: DomainRepository
    @Inject
    lateinit var domainColor: ColorDefinition.DomainColor

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findViewById<View>(R.id.root).setBackgroundColor(domainColor.color.toColor())
        findViewById<TextView>(R.id.textTitle).text = this::class.java.simpleName
        findViewById<ViewGroup>(R.id.root).setOnClickListener {
            startActivity(Intent(this, DomainFragmentsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.textValue).text = domainRepository.getDataList().toString()
    }
}