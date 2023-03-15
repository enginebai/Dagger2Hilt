package com.enginebai.poc.ui.domain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseFragment
import com.enginebai.core.di.Injectable
import com.enginebai.poc.R
import com.enginebai.poc.data.DomainRepository
import com.google.android.material.snackbar.Snackbar
import dagger.Module
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
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DomainFragmentViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DomainFragmentViewModel::class.java]

        Snackbar.make(view, "${domainRepository.getDataList()}", Snackbar.LENGTH_SHORT).show()
        view.findViewById<TextView>(R.id.textTitle).text = DomainFragment::class.java.simpleName

        viewModel.data.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textValue).text = it
        }
        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            viewModel.addData()
        }
    }
}