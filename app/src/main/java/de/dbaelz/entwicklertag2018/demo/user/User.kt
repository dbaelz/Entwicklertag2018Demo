package de.dbaelz.entwicklertag2018.demo.user

typealias Username = String

// The data class provides some additional, generated features
// like equals()/hashCode(), toString() and destructuring
data class User(val username: Username,
                val description: String? = "",
                val points: Int
)
