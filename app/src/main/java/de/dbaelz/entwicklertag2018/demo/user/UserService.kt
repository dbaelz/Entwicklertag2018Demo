package de.dbaelz.entwicklertag2018.demo.user

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object UserService {
    private val userApi: UserApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        userApi = retrofit.create(UserApi::class.java)
    }

    fun getUser(username: Username): Call<User> = userApi.getUser(username)

    fun addUser(user: User): Call<User> {
        return userApi.addUser(user)
    }
}


fun User.hasMasterLevel(): Boolean = points >= 75