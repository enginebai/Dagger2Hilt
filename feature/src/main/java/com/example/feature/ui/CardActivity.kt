package com.example.feature.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.enginebai.core.base.BaseActivity
import com.example.feature.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardActivity : BaseActivity() {

    private val viewModel: CardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        viewModel.play()
        viewModel.card.observe(this) {
            findViewById<TextView>(R.id.textValue).text = it
        }
    }
}