package de.dbaelz.entwicklertag2018.demo.user

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    companion object {
        private const val USER_RESOURCE = "user"
    }

    @GET(USER_RESOURCE)
    fun getAllUsers(): Call<List<User>>

    @GET("$USER_RESOURCE/{username}")
    fun getUser(@Path("username") username: Username): Call<User>

    @POST(USER_RESOURCE)
    fun addUser(@Body user: User): Call<User>
}