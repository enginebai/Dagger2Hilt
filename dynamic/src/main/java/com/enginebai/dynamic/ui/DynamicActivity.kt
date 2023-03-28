package com.enginebai.dynamic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ListNode
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseActivity
import com.enginebai.core.card.Card
import com.enginebai.dynamic.R
import com.enginebai.dynamic.di.DynamicFeatureComponent
import com.enginebai.dynamic.di.DynamicFeatureModule
import com.enginebai.poc.ui.domain.DomainFragmentViewModel
import javax.inject.Inject

class DynamicActivity : BaseActivity() {

    @Inject
    lateinit var list: ListNode<Card>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DynamicViewModel>

    private lateinit var viewModel: DynamicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicFeatureComponent.inject(this, DynamicFeatureModule())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)
        viewModel = ViewModelProvider(this, viewModelFactory)[DynamicViewModel::class.java]

        val textView = findViewById<TextView>(R.id.textValue)
        textView.text = list.toString()
        viewModel.textColor.observe(this) {
            textView.setTextColor(it)
        }
    }
}