package com.enginebai.poc.ui.domain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R
import com.enginebai.poc.ui.singleton.SingletonFragment1

class DomainMultipleFragmentsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domain_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, DomainFragment1())
            .commit()
    }
}