package com.enginebai.poc.ui.singleton

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.enginebai.core.base.BaseFragment
import com.enginebai.poc.MyApplication
import com.enginebai.poc.R
import com.enginebai.poc.data.UserDataHelper
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingletonFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingletonFragment1 : BaseFragment() {

    @Inject
    lateinit var userDataHelper: UserDataHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singleton1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.textViewSingleton1).text = userDataHelper.getUser().id
        view.findViewById<Button>(R.id.buttonNext).setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, SingletonFragment2())
                .addToBackStack(SingletonFragment2::class.java.simpleName)
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SingletonFragment1()
    }
}