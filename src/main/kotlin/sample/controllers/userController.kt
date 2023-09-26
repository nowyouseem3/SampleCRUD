package sample.controllers

import sample.models.*
import sample.config.DBConfig
import sample.models.getUsers
import java.sql.ResultSet
import sample.Queries.*

class userController {

    fun getUserData(): MutableList<getUsers>{
        val data = mutableListOf<getUsers>()
        val query = DBConfig().connect().prepareStatement(readUserQuery)
        print("----------------------------------$query")
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
        return data
    }

    fun createUser (fullName: String, userAddress: String, userName: String, userPassword: String ) :ResultSet? {
        val query = DBConfig().connect().prepareStatement(createUserQuery)
        query.setString(1,fullName)
        query.setString(2, userName)
        query.setString(3, userPassword)
        query.setString(4, userPassword)
        return query.executeQuery()

    }

    fun userDelete(id: Int): ResultSet? {
        val query = DBConfig().connect().prepareStatement(deleteUserQuery)
        query.setInt(1, id)
        // the query is executed and results are fetched
        return query.executeQuery()
    }

    fun userUpdate(fullname: String, userAddress: String, userName: String, userPassword: String, userID: Int) : ResultSet? {
        val query = DBConfig().connect().prepareStatement(updateUserQuery)
        query.setString(1, fullname)
        query.setString(2, userName)
        query.setString(3, userPassword)
        query.setString(4, userAddress)
        query.setInt(5, userID)
        return query.executeQuery()
    }
}