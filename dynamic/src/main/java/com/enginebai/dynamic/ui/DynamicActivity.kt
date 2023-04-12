package com.enginebai.dynamic.ui

import android.os.Bundle
import android.widget.TextView
import com.enginebai.core.ListNode
import com.enginebai.core.base.BaseActivity
import com.enginebai.core.card.Card
import com.enginebai.core.util.ColorDefinition
import com.enginebai.dynamic.DynamicInstance
import com.enginebai.dynamic.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DynamicActivity : BaseActivity() {

    private val list: ListNode<Card> by inject()
    private val domainColor: ColorDefinition.DomainColor by inject()
    private val dynamicInstance: DynamicInstance by inject()
    private val viewModel: DynamicViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)

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