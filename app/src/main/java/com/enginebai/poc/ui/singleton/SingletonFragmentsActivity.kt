package com.enginebai.poc.ui.singleton

import android.os.Bundle
import android.widget.Toast
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R
import com.enginebai.poc.util.getCurrentUser

class SingletonFragmentsActivity : BaseActivity() {

    val pageCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, SingletonFragment.newInstance(0))
            .commit()
        Toast.makeText(this, "Activity Extension: ${getCurrentUser().toString()}", Toast.LENGTH_SHORT).show()
    }
}