package sample.models

import io.ktor.network.sockets.*
import kotlinx.serialization.Serializable
import java.beans.Customizer

@Serializable
data class getUsers(
    var id : Int,
    var fullName : String,
    var userName : String,
    var password : String,
    var address : String
)

@Serializable
data class validateUsers(
    var fullName : String,
)

@Serializable
data class postUsers(
    var fullName : String,
    var userName : String,
    var password : String,
    var address : String
)
/*
@Serializable
var users = mutableListOf<Users>(
    Users(1,"Sidrick Junsay", "Junsay", "Root", "Manila, Philippines")
)*/
