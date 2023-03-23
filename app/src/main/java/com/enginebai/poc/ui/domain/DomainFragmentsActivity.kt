package com.enginebai.poc.ui.domain

import android.os.Bundle
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R
import com.enginebai.poc.data.user.User
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DomainFragmentsActivity : BaseActivity() {

    @Inject
    lateinit var domainUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, DomainFragment.newInstance(domainUser))
            .commit()
    }
}