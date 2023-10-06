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
data class userDetail(
    var userId: Int,
    var FullName: String,
    var data: MutableList<getUserTestWorkExperience>
)

@Serializable
data class getUserTestWorkExperience (
    val recordId: Int, // Serialize (auto increment)
    val userId: Int, // FK of userId in userDetail
    val companyName: String,
    val jobTitle: String,
    val companyAddress: String,
    val yearsOfExperiences: String
)

@Serializable
data class insertperson (
    val fullName: String,
    val userId: Int, // FK of userId in userDetail
    val companyName: String,
    val jobTitle: String,
    val companyAddress: String,
    val yearsOfExperiences: String
)


@Serializable
data class getUserLogin(
    //var userId: Int,
    var userEmail: String,
    var userPassword: String
)

@Serializable
data class PostUsers(
    var userID: Int,
    var userEmail : String,
    var userPassword : String,
    var userFullName : String,
    var userPhone : String,
    var userAddress : String
)

@Serializable
data class putUsers(
    var userEmail : String,
    var userPassword : String,
    var userFullName : String,
    var userPhone : String,
    var userAddress : String
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
