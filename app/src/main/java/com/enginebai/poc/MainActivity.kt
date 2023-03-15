package com.enginebai.poc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.Calendar
import javax.inject.Inject

class MainActivity : BaseActivity(), HasAndroidInjector {

    @Inject
    lateinit var calendar: Calendar

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.buttonStartSingleton).setOnClickListener {
            startActivity(Intent(this, SingletonFragmentsActivity::class.java))
        }
        findViewById<Button>(R.id.buttonStartDomain).setOnClickListener {
            startActivity(Intent(this, DomainActivity::class.java))
        }
        findViewById<Button>(R.id.buttonChangeDomain).setOnClickListener {
            (application as MyApplication).instantiateDomainComponent()
        }
        Toast.makeText(this, calendar.time.toString(), Toast.LENGTH_SHORT).show()
    }


}