package com.enginebai.poc.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.pickRandomTopic
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import javax.inject.Inject

@SuppressLint("SetTextI18n")
class RandomTopicButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle), KoinComponent {

    private var count = 0

    private val domainRepository: DomainRepository by inject()

    init {
        (context.applicationContext as MyApplication).domainComponent().inject(this)
        val topic = pickRandomTopic()
        text = "${topic.courseName}-$count"
        textSize = 20f
        setTextColor(Color.WHITE)
        gravity = Gravity.CENTER

        setOnClickListener {
            // Re-inject with new instance when changing the domain
            (context.applicationContext as MyApplication).domainComponent().inject(this)
            domainRepository.addTopic(topic)
            count++
            text = "${topic.courseName}-$count"
            Log.d(RandomTopicButton::class.java.simpleName, "Add random topic $topic to domain repository.")
            val repo = (context.applicationContext as MyApplication).domainComponent().domainRepository();
            Toast.makeText(context, "${repo.getDataList()}", Toast.LENGTH_SHORT).show()
        }
    }
}