package com.enginebai.user.data

import java.util.UUID

interface UserApi {
    fun getUserList(): List<User>
    fun getUserDetail(id: String): User?
    fun setUser(user: User)
}

class UserApiInMemory(
    private val userDataHelper: UserDataHelper,
    private val nameIdea: UUID
) : UserApi {
    private val userMap = hashMapOf<String, User>()

    override fun getUserList(): List<User> {
        for (i in 1..10) {
            val userId = userDataHelper.getUserIdPrefix()
            userMap[userId] = User(
                "$userId $i",
                nameIdea.toString(),
                i
            )
        }
        return userMap.values.toList()
    }

    override fun getUserDetail(id: String): User? {
        return userMap[id]
    }

    override fun setUser(user: User) {
        userMap[user.name]
    }
}