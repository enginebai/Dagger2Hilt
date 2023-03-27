package com.enginebai.poc.ui.widget

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.enginebai.core.base.BaseViewModel
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.enginebai.poc.util.ColorMixer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class RandomTopicItem(
    private val context: Context,
) {

    @Inject
    lateinit var viewModel: RandomTopicItemViewModel

    @Inject
    lateinit var colorMixer: ColorMixer

    init {
        (context.applicationContext as MyApplication).domainComponent().inject(this)
    }

    fun attach(parentView: ViewGroup, onClickListener: () -> Unit) {
        val button = AppCompatButton(context).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            setOnClickListener {
                viewModel.addTopic()
                onClickListener.invoke()
                Toast.makeText(parentView.context, "${colorMixer.singletonColor}", Toast.LENGTH_SHORT).show()
            }
        }
        parentView.addView(button)
        parentView.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
            viewModel.topic.observe(lifecycleOwner) {
                button.text = it.courseName
            }
        }
    }
}

class RandomTopicItemViewModel @Inject constructor(
    private val domainRepository: DomainRepository
): BaseViewModel() {

    private val _topic = MutableLiveData<DomainTopic>()
    val topic: LiveData<DomainTopic> = _topic

    init {
        _topic.value = pickRandomTopic()
    }

    fun addTopic() {
        _topic.value?.let {
            domainRepository.addTopic(it)
        }
        _topic.value = pickRandomTopic()
    }
}

