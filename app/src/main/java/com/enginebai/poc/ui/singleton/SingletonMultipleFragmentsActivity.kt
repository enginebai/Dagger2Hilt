package com.enginebai.poc.ui.singleton

import android.os.Bundle
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R

class SingletonMultipleFragmentsActivity : BaseActivity() {

    val pageCount = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, SingletonFragment.newInstance(0))
            .commit()
    }
}

