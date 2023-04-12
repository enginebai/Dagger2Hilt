package com.enginebai.dynamic.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ListNode
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseActivity
import com.enginebai.core.card.Card
import com.enginebai.core.util.ColorDefinition
import com.enginebai.dynamic.DynamicInstance
import com.enginebai.dynamic.R
import com.enginebai.dynamic.di.DynamicFeatureComponent
import com.enginebai.dynamic.di.DynamicFeatureModule
import org.koin.android.ext.android.inject
import javax.inject.Inject

class DynamicActivity : BaseActivity() {

    @Inject
    lateinit var list: ListNode<Card>

    private val domainColor: ColorDefinition.DomainColor by inject()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DynamicViewModel>

    private val dynamicInstance by inject<DynamicInstance>()

    private lateinit var viewModel: DynamicViewModel

    // Shared the component between activity and fragment
    lateinit var component: DynamicFeatureComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        component = DynamicFeatureComponent.createComponent(this, DynamicFeatureModule())
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)
        viewModel = ViewModelProvider(this, viewModelFactory)[DynamicViewModel::class.java]

        val textValue = findViewById<TextView>(R.id.textValue)
        val textCardList = findViewById<TextView>(R.id.textCardList)
        textValue.text = list.toString()
        viewModel.textColor.observe(this) {
            textValue.setTextColor(it)
            textCardList.setBackgroundColor(it)
        }
        textCardList.setTextColor(domainColor.color.toColor())
        viewModel.cardList.observe(this) {
            textCardList.text = it
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, DynamicFragment())
            .commit()

        findViewById<TextView>(R.id.textDynamicInstance).text = dynamicInstance.toString()
    }
}