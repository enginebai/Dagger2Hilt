package com.enginebai.poc.ui.domain

import android.os.Bundle
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R

class DomainFragmentsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, DomainFragment.newInstance())
            .commit()
    }
}