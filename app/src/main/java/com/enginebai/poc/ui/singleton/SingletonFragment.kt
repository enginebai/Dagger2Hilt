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
import com.enginebai.core.di.MyFragmentScope
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.ui.domain.DomainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

private const val PAGE_INDEX = "page_index"

@Module
abstract class SingletonFragmentBuilderModule {
    @MyFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSingletonFragment(): SingletonFragment
}

class SingletonFragment : BaseFragment() {

    private var pageIndex: Int = 0

    @Inject
    lateinit var userDataHelper: UserDataHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

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
        view.findViewById<TextView>(R.id.textValue).text = userDataHelper.getUser().id
        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            (requireActivity() as SingletonFragmentsActivity).let { activity ->
                if (pageIndex < activity.pageCount) {
                    val nextIndex = pageIndex + 1
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