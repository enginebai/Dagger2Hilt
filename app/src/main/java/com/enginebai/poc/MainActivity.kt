package com.enginebai.poc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseActivity
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.data.user.TimeMachine
import com.enginebai.poc.ui.domain.DomainActivity
import com.enginebai.poc.ui.singleton.SingletonFragmentsActivity
import com.enginebai.poc.ui.widget.RandomTopicItem
import com.enginebai.poc.util.ColorMixer
import com.example.feature.ui.CardActivity
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

    @Inject
    lateinit var colorMixer: ColorMixer

    @Inject
    lateinit var factory: ViewModelFactory<MainViewModel>
    private lateinit var viewModel: MainViewModel

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
        findViewById<Button>(R.id.buttonMagic).setOnClickListener {
            TimeMachine.becomeYounger(this)
            setAppColors(colorMixer.mixColor(this))
        }
        findViewById<Button>(R.id.buttonCardFeature).setOnClickListener {
            startActivity(Intent(this, CardActivity::class.java))
        }
        Toast.makeText(
            this,
            "${viewModel.greeting()}. Now is ${dateFormat.format(calendar.time)}",
            Toast.LENGTH_SHORT
        ).show()
        setAppColors((application as MyApplication).appComponent().appColors())
    }

    private fun setAppColors(appColors: List<ColorDefinition.AppColor>) {
        val views = mutableListOf<View>()
        views.add(findViewById<ViewGroup>(R.id.root))
        views.add(findViewById<ViewGroup>(R.id.buttonStartSingleton))
        views.add(findViewById<ViewGroup>(R.id.buttonStartDomain))
        views.add(findViewById<ViewGroup>(R.id.buttonChangeDomain))
        views.add(findViewById<ViewGroup>(R.id.buttonRandomTopic))
        views.add(findViewById<ViewGroup>(R.id.buttonMagic))
        views.add(findViewById<ViewGroup>(R.id.buttonCardFeature))
        assert(views.size <= appColors.size)
        for (i in 0 until views.size) {
            views[i].setBackgroundColor(appColors[i].color.toColor())
        }
    }
}