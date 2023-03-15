package com.enginebai.poc.ui.domain

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.enginebai.core.base.BaseFragment
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.ui.singleton.SingletonFragment
import javax.inject.Inject

class DomainFragment : BaseFragment() {

    companion object {
        fun newInstance() = DomainFragment()
    }

    private lateinit var viewModel: DomainFragmentViewModel

    @Inject
    lateinit var domainRepository: DomainRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(DomainFragmentViewModel::class.java)
        (requireActivity().application as MyApplication).domainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textTitle).text = DomainFragment::class.java.simpleName
        view.findViewById<TextView>(R.id.textValue).text = domainRepository.getDataList().toString()
    }
}