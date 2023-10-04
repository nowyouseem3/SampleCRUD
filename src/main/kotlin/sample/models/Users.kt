package sample.models

import io.ktor.network.sockets.*
import kotlinx.serialization.Serializable
import java.beans.Customizer

@Serializable
data class getUsers(
    var id : Int,
    var userEmail : String,
    var userPassword : String,
    var userFullName : String,
    var userPhone: String,
    var userAddress : String
)
@Serializable
data class getUsersTest(
    var userId: Int,
    var title: String,
    var body: String
)

@Serializable
data class getUserLogin(
    //var userId: Int,
    var username: String,
    var password: String
)

@Serializable
data class PostUsers(
    var userID: Int,
    var userEmail : String,
    var password : String,
    var fullName : String,
    var phone : String,
    var address : String
)

@Serializable
data class putUsers(
    var fullName : String,
    var userName : String,
    var password : String,
    var address : String
)

@Serializable
data class textResponse(
    var text: String = "",
    var code: Int = 0
)
/*
@Serializable
var users = mutableListOf<Users>(
    Users(1,"Sidrick Junsay", "Junsay", "Root", "Manila, Philippines")
)*/
