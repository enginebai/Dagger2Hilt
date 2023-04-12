package com.enginebai.poc.ui.domain

import android.os.Bundle
import com.enginebai.core.base.BaseActivity
import com.enginebai.poc.R
import com.enginebai.poc.data.user.User
import dagger.android.AndroidInjection.inject
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import org.koin.android.ext.android.inject
import javax.inject.Inject

class DomainFragmentsActivity : BaseActivity(), HasAndroidInjector {

    private val domainUser: User by inject()

    // For dagger.android
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_multiple_fragments)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, DomainFragment.newInstance(domainUser))
            .commit()
    }
}