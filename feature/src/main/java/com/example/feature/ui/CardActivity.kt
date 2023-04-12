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
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class CardActivity : BaseActivity() {

    private val viewModel: CardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        viewModel.play()
        viewModel.card.observe(this) {
            findViewById<TextView>(R.id.textValue).text = it
        }
    }
}