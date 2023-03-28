package com.enginebai.dynamic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.enginebai.core.ListNode
import com.enginebai.core.base.BaseActivity
import com.enginebai.core.card.Card
import com.enginebai.dynamic.R
import com.enginebai.dynamic.di.DynamicFeatureComponent
import com.enginebai.dynamic.di.DynamicFeatureModule
import javax.inject.Inject

class DynamicActivity : BaseActivity() {

    @Inject
    lateinit var list: ListNode<Card>

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicFeatureComponent.inject(this, DynamicFeatureModule())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic)
        findViewById<TextView>(R.id.textValue).text = list.toString()
    }
}