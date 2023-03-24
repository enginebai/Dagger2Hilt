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
import com.enginebai.poc.data.user.User
import javax.inject.Inject

@SuppressLint("SetTextI18n")
class RandomTopicButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatButton(context, attrs, defStyle) {

    @Inject
    lateinit var username: String
    @Inject
    lateinit var domainUser: User

    private var count = 0

    init {
        (context.applicationContext as MyApplication).appComponent().inject(this)
        (context.applicationContext as MyApplication).domainComponent().inject(this)

        val topic = pickRandomTopic()
        text = "${topic.courseName}-$count"
        textSize = 20f
        setTextColor(Color.WHITE)
        gravity = Gravity.CENTER

        setOnClickListener {
            val domainRepository = (context.applicationContext as MyApplication).domainComponent().domainRepository()
            domainRepository.addTopic(topic)
            count++
            text = "${topic.courseName}-$count"
            Log.d(RandomTopicButton::class.java.simpleName, "Add random topic $topic to domain repository.")
            Toast.makeText(context, "$username, $domainUser, ${domainRepository.getDataList()}", Toast.LENGTH_SHORT).show()
        }
    }
}