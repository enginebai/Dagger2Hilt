package com.enginebai.poc.ui.singleton

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.enginebai.core.base.BaseFragment
import com.enginebai.core.di.Injectable
import com.enginebai.poc.R
import com.enginebai.poc.data.user.UserDataHelper
import javax.inject.Inject

private const val PAGE_INDEX = "page_index"

class SingletonFragment : BaseFragment(), Injectable {

    private var pageIndex: Int = 0

    @Inject
    lateinit var userDataHelper: UserDataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pageIndex = it.getInt(PAGE_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textTitle).text = "${SingletonFragment::class.java.simpleName}-$pageIndex"
        view.findViewById<TextView>(R.id.textValue).text = userDataHelper.getUser().toString()
        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            (requireActivity() as SingletonFragmentsActivity).let { activity ->
                val nextIndex = pageIndex + 1
                if (nextIndex < activity.pageCount) {
                    activity.supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragmentContainer, SingletonFragment.newInstance(nextIndex))
                        .addToBackStack("${SingletonFragment::class.java.simpleName}$nextIndex")
                        .commit()
                } else {
                    startActivity(Intent(activity, SingletonDetailActivity::class.java))
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(pageIndex: Int = 0) =
            SingletonFragment().apply {
                arguments = Bundle().apply {
                    putInt(PAGE_INDEX, pageIndex)
                }
            }
    }
}