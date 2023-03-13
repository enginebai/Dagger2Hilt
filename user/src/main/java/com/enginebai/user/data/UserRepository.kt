package com.enginebai.user.data

interface UserRepository {
    fun getUserList(): List<User>
    fun getUserDetail(id: String): User?
    fun setUser(user: User)
}

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {
    override fun getUserList(): List<User> {
        return userApi.getUserList()
    }

    override fun getUserDetail(id: String): User? {
        return userApi.getUserDetail(id)
    }

    override fun setUser(user: User) {
        userApi.setUser(user)
    }

}