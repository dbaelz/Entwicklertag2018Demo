package de.dbaelz.entwicklertag2018.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import de.dbaelz.entwicklertag2018.demo.user.User
import de.dbaelz.entwicklertag2018.demo.user.UserService
import de.dbaelz.entwicklertag2018.demo.user.hasMasterLevel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onButtonClicked()
    }


    private fun onButtonClicked() {
        button_showUser.setOnClickListener {
            val username = editText_username.text.toString()

            if (username.isNotEmpty()) {
                UserService.getUser(username).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, throwable: Throwable?) {
                        Log.d("UserService.getUser", "onFailure -> $throwable")
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>?) {
                        Log.d("UserService.getUser", "onResponse -> $response - body -> ${response?.body()}")

                        response?.body()?.apply {
                            cardView_username.text = username
                            description?.isNotEmpty().apply {
                                cardView_description.text = description
                            }
                            cardView_points.text = getString(R.string.cardView_points_text, points)

                            if (hasMasterLevel()) {
                                cardView_username.setTextColor(getColor(R.color.colorAccent))
                                cardView_points.setTextColor(getColor(R.color.colorAccent))
                            }
                        }
                    }
                })
            }
        }
    }
}
