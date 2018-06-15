package de.dbaelz.entwicklertag2018.demo.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @GET("user")
    fun getAllUsers(): Call<List<User>>

    @GET("user/{username}")
    fun getUser(@Path("username") username: Username): Call<User>

    @POST("user")
    fun addUser(@Body user: User): Call<User>
}