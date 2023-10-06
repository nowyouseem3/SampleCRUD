package sample.controllers

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import sample.models.*
import sample.config.DBConfig
import sample.models.getUsers
import sample.Queries.*
import sample.validation.passwordHash

class userController {

    // API to API
    suspend fun test() : List<getUsersTest>{
        val client = HttpClient(CIO)

        val json = Json{ignoreUnknownKeys = true}
        val response = client.get("https://jsonplaceholder.typicode.com/posts")
        val userclient = json.decodeFromString<List<getUsersTest>>(response.body())

        return userclient
    }

    // to get all data from database
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

    // insert data to database
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

    // delete data to database
    fun userDelete(id: Int): Int {
        val query = DBConfig().connect().prepareStatement(deleteUserQuery)
        // set id from userDelete params to deleteUserQuery
        query.setInt(1, id)
        query.setInt(2, id)

        // the query is executed and results are fetched
        return query.executeUpdate()
    }

    fun userUpdate(Email: String, Password: String, fullName: String, Phone: String, Address: String, id: Int) : Int{
        val query = DBConfig().connect().prepareStatement(updateUserQuery)
        // set fullname from userUpdate params to updateUserQuery
        query.setString(1, Email)
        // set username from userUpdate params to updateUserQuery
        query.setString(2, passwordHash(Password))
        query.setInt(3,id)
        // set password from userUpdate params to updateUserQuery
        query.setString(4, fullName)
        // set address from userUpdate params to updateUserQuery
        query.setString(5, Phone)
        query.setString(6, Address)
        query.setInt(7,id)

        // the query is executed and results are fetched
        return query.executeUpdate()
    }
}