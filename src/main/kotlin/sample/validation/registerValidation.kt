@file:Suppress("SENSELESS_COMPARISON")

package sample.validation

import org.mindrot.jbcrypt.BCrypt
import sample.Queries.readUserQuery
import java.util.regex.Matcher
import java.util.regex.Pattern
import sample.config.*

fun passwordHash(password: String) :String {
    // password encryption
    return BCrypt.hashpw(password, BCrypt.gensalt())
}
fun userValidation (userEmail: String, fullName: String, password: String) : Boolean {
    // regex is declared variable to check if the user data meet the requirements
    val regex = "^[A-Za-z]\\w+.*\\w{1,29}$"

    //getting all information from DB to validate if the input Data has similar to usersDB
    val query = DBConfig().connect().prepareStatement(readUserQuery)
    val result = query.executeQuery()

    // Compile the ReGex
    val userPattern : Pattern = Pattern.compile(regex)

    // If the username is empty and already in DB
    // return false
    while (result.next()) {
        // username,fullname are declared variable to store the said items in every single loop
        val useremail = result.getString("useremail")
        val fullname = result.getString("userfullname")
        //validation
        if (userEmail == useremail || fullName == fullname ||
            password.length < 3 || userEmail.length < 3) return false
    }

    // Pattern class contains matcher() method
    // to find matching between given username
    // and regular expression.
    val userMatch : Matcher = userPattern.matcher(fullName)

    // Return if the username
    // matched the ReGex
    return userMatch.matches()
}


