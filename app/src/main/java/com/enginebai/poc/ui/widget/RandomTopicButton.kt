package com.enginebai.poc.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.domain.pickRandomTopic
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class RandomTopicButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    private var count = 0

//    @Inject
//    lateinit var domainRepository: DomainRepository

    init {
//        (context.applicationContext as MyApplication).domainComponent().inject(this)
        val topic = pickRandomTopic()
        text = "${topic.courseName}-$count"
        textSize = 20f
        setTextColor(Color.WHITE)
        gravity = Gravity.CENTER

        setOnClickListener {
            // Re-inject with new instance when changing the domain
            val repo = (context.applicationContext as MyApplication).domainCustomComponent().domainRepository()
            repo.addRandomTopic()
//            domainRepository.addTopic(topic)
            count++
            text = "${topic.courseName}-$count"
            Log.d(RandomTopicButton::class.java.simpleName, "Add random topic $topic to domain repository.")
//            val repo = (context.applicationContext as MyApplication).domainComponent().domainRepository();
//            Toast.makeText(context, "${repo.getDataList()}", Toast.LENGTH_SHORT).show()
        }
    }
}