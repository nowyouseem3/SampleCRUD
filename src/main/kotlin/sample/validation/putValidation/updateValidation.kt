package sample.validation.putValidation


import java.util.regex.Matcher
import java.util.regex.Pattern
import sample.config.*

fun userPutNameValidation (userName: String, userID: Int) :Boolean{
    val regex = "^[A-Za-z]\\w{5,29}$"
    val query = DBConfig().connect().prepareStatement("SELECT username FROM users WHERE id != '$userID'")
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


