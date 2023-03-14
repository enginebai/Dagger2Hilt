package com.enginebai.poc.ui.domain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.enginebai.poc.R

class DomainFragment1 : Fragment() {

    companion object {
        fun newInstance() = DomainFragment1()
    }

    private lateinit var viewModel: DomainFragment1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_domain_fragment1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DomainFragment1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}