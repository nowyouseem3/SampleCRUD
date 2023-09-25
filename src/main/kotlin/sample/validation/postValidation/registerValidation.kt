package sample.validation.postValidation

import java.util.regex.Matcher
import java.util.regex.Pattern
import sample.config.*

fun userNameValidation (userName: String) :Boolean{
    val regex = "^[A-Za-z]\\w{5,29}$"
    val query = DBConfig().connect().prepareStatement("SELECT * FROM users")
    val result = query.executeQuery()
    // Compile the ReGex
    val userPattern : Pattern = Pattern.compile(regex)

    // If the username is empty and already in DB
    // return false
    while (result.next()) {
        val username = result.getString("username")

        if (userName == null || userName == username) {
            return false
        }
    }

    // Pattern class contains matcher() method
    // to find matching between given username
    // and regular expression.
    val match : Matcher = userPattern.matcher(userName)

    // Return if the username
    // matched the ReGex
    return match.matches()
}


