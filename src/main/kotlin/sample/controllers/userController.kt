package sample.controllers

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.internal.decodeStringToJsonTree
import org.mindrot.jbcrypt.BCrypt
import sample.models.*
import sample.config.DBConfig
import sample.models.getUsers
import java.sql.ResultSet
import sample.Queries.*
import sample.validation.passwordHash
import java.net.http.HttpResponse

class userController {

    suspend fun test() : List<getUsersTest>{
        val client = HttpClient(CIO)

        val json = Json{ignoreUnknownKeys = true}
        val response = client.get("https://jsonplaceholder.typicode.com/posts")
        val userclient = json.decodeFromString<List<getUsersTest>>(response.body())

        return userclient
    }
    fun getUserData(): MutableList<getUsers>{
        val data = mutableListOf<getUsers>()
        val query = DBConfig().connect().prepareStatement(readUserQuery)
        val result = query.executeQuery()
        while (result.next()) {
            // getting the value of the id column
            val id:Int = result.getInt("userid")
            // getting the value of the name column
            val useremail = result.getString("useremail")
            // getting the value of the name column
            val password = result.getString("userpassword")
            // getting the value of the name column
            val fullName = result.getString("userfullname")
            // getting the value of the name column
            val phone = result.getString("userphone")
            // getting the value of the name column
            val address = result.getString("useraddress")
            data.add(getUsers(id,useremail,password,fullName,phone,address))
        }
        // the query is executed and results are fetched
        return data
    }

    fun createUser (userId: Int, userEmail: String, userPassword: String, userFullName: String, userPhone: String, userAddress: String ){
        val accountQuery = DBConfig().connect().prepareStatement(createUserAccountQuery)
        val credentialQuery = DBConfig().connect().prepareStatement(createUserCredentialQuery)

        // set account query value
        accountQuery.setInt(1,userId)
        accountQuery.setString(2,userEmail)
        accountQuery.setString(3, passwordHash(userPassword))
        // set credential query value
        credentialQuery.setInt(1, userId)
        credentialQuery.setString(2,userFullName)
        credentialQuery.setString(3, userPhone)
        credentialQuery.setString(4, userAddress)

        credentialQuery.executeUpdate()
        accountQuery.executeUpdate()
    }

    fun userDelete(id: Int): ResultSet? {
        val query = DBConfig().connect().prepareStatement(deleteUserQuery)
        // set id from userDelete params to deleteUserQuery
        query.setInt(1, id)

        // the query is executed and results are fetched
        return query.executeQuery()
    }

    fun userUpdate(fullname: String, userAddress: String, userName: String, userPassword: String, userID: Int) :ResultSet? {
        val query = DBConfig().connect().prepareStatement(updateUserQuery)
        // set fullname from userUpdate params to updateUserQuery
        query.setString(1, fullname)
        // set username from userUpdate params to updateUserQuery
        query.setString(2, userName)
        // set password from userUpdate params to updateUserQuery
        query.setString(3, passwordHash(userPassword))
        // set address from userUpdate params to updateUserQuery
        query.setString(4, userAddress)
        query.setInt(5, userID)

        // the query is executed and results are fetched
        return query.executeQuery()
    }
}