package de.dbaelz.entwicklertag2018.demo.user

typealias Username = String

data class User(val username: Username,
                val description: String? = "",
                val points: Int
)
