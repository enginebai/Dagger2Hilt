package com.enginebai.poc.ui.domain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.enginebai.core.base.BaseFragment
import com.enginebai.poc.R
import com.enginebai.poc.ui.singleton.SingletonFragment2

class DomainFragment1 : BaseFragment() {

    companion object {
        fun newInstance() = DomainFragment1()
    }

    private lateinit var viewModel: DomainFragment1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_domain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            Log.d("qwer", "Click")
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, DomainFragment2())
                .addToBackStack(DomainFragment2::class.java.simpleName)
                .commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DomainFragment1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}