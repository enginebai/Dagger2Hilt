package com.enginebai.poc.ui.singleton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R

class SingletonMultipleFragmentsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singeton_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, SingletonFragment1())
            .commit()
    }
}

