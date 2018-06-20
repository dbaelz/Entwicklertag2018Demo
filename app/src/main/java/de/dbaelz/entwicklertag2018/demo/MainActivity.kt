package de.dbaelz.entwicklertag2018.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import de.dbaelz.entwicklertag2018.demo.user.User
import de.dbaelz.entwicklertag2018.demo.user.UserService
import de.dbaelz.entwicklertag2018.demo.user.hasMasterLevel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.setContentView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_showUser.setOnClickListener {
            onButtonClicked()
        }
    }

    private fun onButtonClicked() {
        // Access of the EditText text property with the property syntax instead auf getText()
        val username = editText_username.text.toString()

        // isNotEmpty(): Another simple, but useful Extension function provided by the Kotlin Standard Library
        if (username.isNotEmpty()) {
            UserService.getUser(username).enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, throwable: Throwable?) {
                    Log.d("UserService.getUser", "onFailure -> $throwable") // String template with a variable
                    cardView.visibility = View.GONE
                }

                override fun onResponse(call: Call<User>, response: Response<User>?) {
                    // String template with  an expression
                    Log.d("UserService.getUser", "onResponse -> $response - body -> ${response?.body()}")

                    // ?.let: Safe-call operator (only called when not null) and the let extension function are often used
                    // in combination to perform operations on an object when it's not null.
                    // The scope of the lambda (this) is the outer scope (Callback<User>).
                    // We could access the ResonseBody with the "it" property.
                    response?.errorBody()?.let {
                        cardView.visibility = View.GONE
                        return
                    }

                    // An alternative to let is apply. This is a "Function Literal with Receiver" (simply spoken an
                    // Extension Function with another function as parameter).
                    // The scope of the lambda (this) is the User object and we could
                    // access the properties of the user directly.
                    response?.body()?.apply {
                        cardView.visibility = View.VISIBLE

                        cardView_username.text = username
                        description?.isNotEmpty().apply {
                            cardView_description.text = description
                        }
                        cardView_points.text = getString(R.string.cardView_points_text, points)

                        // Another usage of a Extension Function
                        if (hasMasterLevel()) {
                            cardView_username.setTextColor(getColor(R.color.colorAccent))
                            cardView_points.setTextColor(getColor(R.color.colorAccent))
                        }
                    }
                }
            })
        } else {
            cardView.visibility = View.GONE
        }
    }
}
