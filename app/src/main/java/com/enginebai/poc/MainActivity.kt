package com.enginebai.poc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.data.user.TimeMachine
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var calendar: Calendar
    @Inject
    lateinit var dateFormat: DateFormat
//    @Inject
//    lateinit var factory: ViewModelFactory<MainViewModel>
    private val viewModel: MainViewModel by viewModels()

    // For dagger.android
//    @Inject
//    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
//    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        findViewById<Button>(R.id.buttonStartSingleton).setOnClickListener {
            startActivity(Intent(this, SingletonFragmentsActivity::class.java))
        }
        findViewById<Button>(R.id.buttonStartDomain).setOnClickListener {
            DomainActivity.start(this)
        }
        findViewById<Button>(R.id.buttonChangeDomain).setOnClickListener {
            (application as MyApplication).instantiateDomainComponent()
        }
        findViewById<Button>(R.id.buttonBecomeYounger).setOnClickListener {
            TimeMachine.becomeYounger(this)
        }
        Toast.makeText(this, "${viewModel.greeting()}. Now is ${dateFormat.format(calendar.time)}", Toast.LENGTH_SHORT).show()
        setAppColors()
    }

    private fun setAppColors() {
        val appColors = (application as MyApplication).appComponent().appColors()
        val views = mutableListOf<View>()
        views.add(findViewById<ViewGroup>(R.id.root))
        views.add(findViewById<ViewGroup>(R.id.buttonStartSingleton))
        views.add(findViewById<ViewGroup>(R.id.buttonStartDomain))
        views.add(findViewById<ViewGroup>(R.id.buttonBecomeYounger))
        views.add(findViewById<ViewGroup>(R.id.buttonRandomTopic))
        assert(views.size == appColors.size)
        for (i in 0 until views.size) {
            views[i].setBackgroundColor(appColors[i].color.toColor())
        }
    }
}