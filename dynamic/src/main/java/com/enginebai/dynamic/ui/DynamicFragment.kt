package com.enginebai.dynamic.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.enginebai.core.ViewModelFactory
import com.enginebai.core.base.BaseFragment
import com.enginebai.core.util.ColorDefinition
import com.enginebai.dynamic.R
import org.koin.android.ext.android.inject
import javax.inject.Inject

class DynamicFragment : BaseFragment() {

    private lateinit var viewModel: DynamicViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<DynamicViewModel>

    private val domainColor: ColorDefinition.DomainColor by inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = (requireActivity() as DynamicActivity)
        activity.component.inject(this)
        viewModel = ViewModelProvider(activity, viewModelFactory)[DynamicViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dynamic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ViewGroup>(R.id.root).setBackgroundColor(domainColor.color.toColor())
        viewModel.cardList.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textCardList).text = it
        }
        view.findViewById<Button>(R.id.buttonAddToHead).setOnClickListener {
            viewModel.appendTopicToHead()
        }
        view.findViewById<Button>(R.id.buttonAddToRear).setOnClickListener {
            viewModel.appendTopicToRear()
        }
    }
}