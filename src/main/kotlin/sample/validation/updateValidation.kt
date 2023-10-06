package sample.validation

import sample.Queries.readUserUpdateQuery
import java.util.regex.Matcher
import java.util.regex.Pattern
import sample.config.*

fun userPutNameValidation (userEmail: String, userID: Int,fullName: String) :Boolean{
    val regex = "^[A-Za-z]\\w+.*\\w{3,29}$"
    val query = DBConfig().connect().prepareStatement(readUserUpdateQuery)
    query.setInt(1, userID)
    val result = query.executeQuery()
    // Compile the ReGex
    val userPattern : Pattern = Pattern.compile(regex)

    // If the username is empty and already in DB
    // return false
    while (result.next()) {
        val useremail = result.getString("useremail")
        val fullname = result.getString("userfullname")
        if (userEmail == useremail || userEmail.length < 3|| fullName == fullname) return false
    }

    // Pattern class contains matcher() method
    // to find matching between given username
    // and regular expression.
    val match : Matcher = userPattern.matcher(fullName)

    // Return if the username
    // matched the ReGex
    return match.matches()
}


