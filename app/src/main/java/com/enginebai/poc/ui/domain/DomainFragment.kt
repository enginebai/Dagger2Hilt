package com.enginebai.poc.ui.domain

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.enginebai.core.base.BaseFragment
import com.enginebai.poc.R
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.data.user.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val KEY_DOMAIN_USER = "domain_user"

@AndroidEntryPoint
class DomainFragment : BaseFragment() {

    companion object {
        fun newInstance(user: User) = DomainFragment().apply {
            arguments = Bundle().apply{
                putParcelable(KEY_DOMAIN_USER, DomainFragmentUser(user))
            }
        }
    }

    private val viewModel: DomainFragmentViewModel by viewModels()

    @Inject
    lateinit var domainRepository: DomainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textTitle).text =
            "${DomainFragment::class.java.simpleName}\n${domainRepository.getDataList()}"

        viewModel.data.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textValue).text = it
        }
        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            viewModel.addData()
        }
    }
}