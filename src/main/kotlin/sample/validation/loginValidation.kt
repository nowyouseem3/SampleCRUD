package sample.validation

import org.mindrot.jbcrypt.BCrypt
import sample.controllers.userController


fun loginValidation(username: String, password: String) : Boolean {
    // declaring local storage for validating login
    val userStorage = userController().getUserData()

    //validate if username exist and equal to password
    val sessionUser = userStorage.find { it.userName == username} ?: return true
    return !BCrypt.checkpw(password, sessionUser.password)
}