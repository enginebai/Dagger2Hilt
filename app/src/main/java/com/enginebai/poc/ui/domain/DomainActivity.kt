package com.enginebai.poc.ui.domain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.DomainData
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.DomainType
import javax.inject.Inject

class DomainActivity : BaseActivity() {

    @Inject
    lateinit var domainRepository: DomainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).domainComponent.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domain)
        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            startActivity(Intent(this, DomainMultipleFragmentsActivity::class.java))
        }

        domainRepository.addData(DomainData(DomainType.DFS))
        Log.d("qwer", domainRepository.getDataList().toString())
    }
}