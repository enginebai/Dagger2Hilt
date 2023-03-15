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
import com.enginebai.core.di.Injectable
import com.enginebai.core.di.MyFragmentScope
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.DomainData
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.DomainTopic
import com.enginebai.poc.ui.singleton.SingletonFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

@Module
abstract class DomainFragmentBuilderModule {

}

class DomainFragment : BaseFragment(), Injectable {

    companion object {
        fun newInstance() = DomainFragment()
    }

    private lateinit var viewModel: DomainFragmentViewModel

    @Inject
    lateinit var domainRepository: DomainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: check how to instantiate the view model
        viewModel = ViewModelProvider(this).get(DomainFragmentViewModel::class.java)

        view.findViewById<TextView>(R.id.textTitle).text = DomainFragment::class.java.simpleName
        view.findViewById<TextView>(R.id.textValue).text = domainRepository.getDataList().toString()
        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            domainRepository.addRandomData()
        }
    }
}