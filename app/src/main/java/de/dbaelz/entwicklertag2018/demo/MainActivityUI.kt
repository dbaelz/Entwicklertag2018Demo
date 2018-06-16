package de.dbaelz.entwicklertag2018.demo

import android.support.v7.widget.CardView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import de.dbaelz.entwicklertag2018.demo.user.User
import de.dbaelz.entwicklertag2018.demo.user.UserService
import de.dbaelz.entwicklertag2018.demo.user.hasMasterLevel
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            textView(R.string.title_text) {
                gravity = Gravity.CENTER
                textSize = 24f
            }.lparams(width = matchParent, height = wrapContent)

            editText {
                hint = context.getString(R.string.username_hint)
                id = Ids.editText_username
                inputType = 1
                topPadding = dip(8)
            }.lparams(width = matchParent, height = wrapContent)

            themedButton(R.string.button_show, theme = R.style.Widget_AppCompat_Button_Colored) {
                id = Ids.button_showUser
                setOnClickListener {
                    Log.d("onClick", "Do some stuff")
                    onButtonClicked(ui)
                }
            }.lparams(width = matchParent, height = wrapContent) {
                bottomMargin = dip(32)
            }

            cardView {
                id = Ids.cardView
                visibility = View.GONE
                radius = dip(4).toFloat()

                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        gravity = Gravity.CENTER
                        id = Ids.cardView_username
                        textSize = 24f
                    }.lparams(width = matchParent, height = wrapContent)
                    textView {
                        gravity = Gravity.CENTER
                        id = Ids.cardView_points
                        topPadding = dip(4)
                        textSize = 16f
                    }.lparams(width = matchParent, height = wrapContent)

                    textView {
                        id = Ids.cardView_description
                        topPadding = dip(8)
                        textSize = 16f
                    }.lparams(width = matchParent, height = wrapContent)

                }.lparams(width = matchParent, height = matchParent)
            }.lparams(width = matchParent, height = wrapContent) {
                gravity = Gravity.CENTER
            }
        }
    }

    private fun onButtonClicked(ui: AnkoContext<MainActivity>) {
        with(ui.owner) {
            val cardView = find<CardView>(Ids.cardView)
            val cardViewUsername = find<TextView>(Ids.cardView_username)
            val cardViewPoints = find<TextView>(Ids.cardView_points)
            val cardViewDescription = find<TextView>(Ids.cardView_description)

            val username = find<EditText>(Ids.editText_username).text.toString()

            if (username.isNotEmpty()) {
                UserService.getUser(username).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, throwable: Throwable?) {
                        Log.d("UserService.getUser", "onFailure -> $throwable")
                        cardView.visibility = View.GONE
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>?) {
                        Log.d("UserService.getUser", "onResponse -> $response - body -> ${response?.body()}")

                        response?.errorBody()?.let {
                            cardView.visibility = View.GONE
                            return
                        }

                        response?.body()?.apply {
                            cardView.visibility = View.VISIBLE

                            cardViewUsername.text = username
                            description?.isNotEmpty().apply {
                                cardViewDescription.text = description
                            }
                            cardViewPoints.text = getString(R.string.cardView_points_text, points)

                            if (hasMasterLevel()) {
                                cardViewUsername.setTextColor(getColor(R.color.colorAccent))
                                cardViewPoints.setTextColor(getColor(R.color.colorAccent))
                            }
                        }
                    }
                })
            } else {
                cardView.visibility = View.GONE
            }
        }
    }

    private object Ids {
        val button_showUser = 1
        val cardView = 2
        val cardView_description = 3
        val cardView_points = 4
        val cardView_username = 5
        val editText_username = 6
    }
}