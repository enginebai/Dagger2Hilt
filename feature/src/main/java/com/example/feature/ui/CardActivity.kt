package com.example.feature.ui

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseActivity
import com.example.feature.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CardActivity : BaseActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CardViewModel>

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private lateinit var viewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        viewModel = ViewModelProvider(this, viewModelFactory)[CardViewModel::class.java]

        viewModel.play()
        viewModel.card.observe(this) {
            findViewById<TextView>(R.id.textValue).text = it
        }
    }
}