package com.enginebai.poc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class MainActivity : BaseActivity(), HasAndroidInjector {

    @Inject
    lateinit var calendar: Calendar
    @Inject
    lateinit var dateFormat: DateFormat
    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>
    private lateinit var viewModel: MainViewModel

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        findViewById<Button>(R.id.buttonStartSingleton).setOnClickListener {
            startActivity(Intent(this, SingletonFragmentsActivity::class.java))
        }
        findViewById<Button>(R.id.buttonStartDomain).setOnClickListener {
            DomainActivity.start(this)
        }
        findViewById<Button>(R.id.buttonChangeDomain).setOnClickListener {
            (application as MyApplication).instantiateDomainComponent()
        }
        findViewById<Button>(R.id.buttonChangeUserBody).setOnClickListener {
            val body = (application as MyApplication).component().userDataHelper().getUser().body
            body.becomeTallest()
            body.becomeMostWeighted()
        }
        Toast.makeText(this, "${viewModel.greeting()}. Now is ${dateFormat.format(calendar.time)}", Toast.LENGTH_SHORT).show()
    }
}