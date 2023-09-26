@file:Suppress("SENSELESS_COMPARISON")

package sample.validation

import sample.Queries.readUserQuery
import java.util.regex.Matcher
import java.util.regex.Pattern
import sample.config.*

fun userValidation (userName: String, fullName: String, password: String) : Boolean {
    val regex = "^[A-Za-z]\\w{3,29}$"
    val query = DBConfig().connect().prepareStatement(readUserQuery)
    val result = query.executeQuery()
    // Compile the ReGex
    val userPattern : Pattern = Pattern.compile(regex)

    // If the username is empty and already in DB
    // return false
    while (result.next()) {
        val username = result.getString("username")
        val fullname = result.getString("fullname")

        if (userName == null || fullName == null || password == null ||
            userName == username || fullName == fullname ||
            fullName.length < 3 || password.length < 3) {
            return false
        }
    }

    // Pattern class contains matcher() method
    // to find matching between given username
    // and regular expression.
    val userMatch : Matcher = userPattern.matcher(userName)

    // Return if the username
    // matched the ReGex
    return userMatch.matches()
}


