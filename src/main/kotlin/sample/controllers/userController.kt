package sample.controllers

import sample.models.*
import sample.config.DBConfig
import sample.models.getUsers
import java.sql.ResultSet

class userController {

    fun getUserData(): MutableList<getUsers>{
        val data = mutableListOf<getUsers>()
        val query = DBConfig().connect().prepareStatement("SELECT * FROM users")
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
        val query = DBConfig().connect().prepareStatement("INSERT INTO users(fullname, username, password, address) VALUES ('$fullName','$userName','$userPassword','$userAddress')")
        return query.executeQuery()

    }

    fun userDelete(id: String): ResultSet? {
        val query = DBConfig().connect().prepareStatement("DELETE FROM users WHERE id=$id")
        // the query is executed and results are fetched
        return query.executeQuery()
    }

    fun userUpdate(fullname: String, userAddress: String, userName: String, userPassword: String, userID: Int) : ResultSet? {
        val query = DBConfig().connect().prepareStatement("UPDATE users SET fullname='$fullname', username='$userName', password='$userPassword', address='$userAddress' WHERE id='$userID'")
        return query.executeQuery()
    }

}