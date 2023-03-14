package com.enginebai.poc.ui.domain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R

class DomainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_domain)
        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            startActivity(Intent(this, DomainMultipleFragmentsActivity::class.java))
        }
    }
}