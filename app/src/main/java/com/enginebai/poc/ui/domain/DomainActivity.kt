package com.enginebai.poc.ui.domain

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.DomainData
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.DomainTopic
import javax.inject.Inject

class DomainActivity : BaseActivity() {

    @Inject
    lateinit var domainRepository: DomainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).domainComponent.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findViewById<TextView>(R.id.textTitle).text = this::class.java.simpleName
        findViewById<TextView>(R.id.textValue).text = domainRepository.getDataList().toString()
        findViewById<ViewGroup>(R.id.root).setOnClickListener {
            startActivity(Intent(this, DomainFragmentsActivity::class.java))
        }
    }
}