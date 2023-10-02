package sample.controllers

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.internal.decodeStringToJsonTree
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
        print("-----------------$userclient")

        return userclient
    }
    fun getUserData(): MutableList<getUsers>{
        val data = mutableListOf<getUsers>()
        val query = DBConfig().connect().prepareStatement(readUserQuery)
        val result = query.executeQuery()
        while (result.next()) {
            // getting the value of the id column
            val id:Int = result.getInt("id")
            // getting the value of the name column
            val fullName = result.getString("fullname")
            // getting the value of the name column
            val username = result.getString("username")
            // getting the value of the name column
            val password = result.getString("password")
            // getting the value of the name column
            val address = result.getString("address")
            data.add(getUsers(id, fullName, username, password, address))
        }
        // the query is executed and results are fetched
        return data
    }

    fun createUser (fullName: String, userAddress: String, userName: String, userPassword: String ) :ResultSet? {
        val query = DBConfig().connect().prepareStatement(createUserQuery)
        // set fullname from createUser params to createUserQuery
        query.setString(1,fullName)
        // set username from createUser params to createUserQuery
        query.setString(2, userName)
        // set password from createUser params to createUserQuery
        query.setString(3, passwordHash(userPassword))
        // set user address from createUser params to createUserQuery
        query.setString(4, userAddress)

        // the query is executed and results are fetched
        return query.executeQuery()

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