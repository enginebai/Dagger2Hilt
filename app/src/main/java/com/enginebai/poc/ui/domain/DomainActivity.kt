package com.enginebai.poc.ui.domain

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.enginebai.core.base.BaseActivity
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.ui.widget.RandomTopicItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DomainActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            (context.applicationContext as MyApplication).appComponent().userDataHelper().getUser().age = 0
            context.startActivity(Intent(context, DomainActivity::class.java))
        }
    }

    @Inject
    lateinit var domainRepository: DomainRepository
    @Inject
    lateinit var domainColor: ColorDefinition.DomainColor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findViewById<View>(R.id.root).setBackgroundColor(domainColor.color.toColor())
        findViewById<TextView>(R.id.textTitle).text = this::class.java.simpleName
        findViewById<ViewGroup>(R.id.root).setOnClickListener {
            startActivity(Intent(this, DomainFragmentsActivity::class.java))
        }
        addRandomTopicItem()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun addRandomTopicItem() {
        val root = findViewById<ViewGroup>(R.id.root)
        val item = RandomTopicItem(this)
        item.attach(root) { refresh() }
    }

    private fun refresh() {
        findViewById<TextView>(R.id.textValue).text = domainRepository.getDataList().toString()
    }
}