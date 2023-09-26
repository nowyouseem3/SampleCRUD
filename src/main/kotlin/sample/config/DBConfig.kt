package sample.config

import java.sql.Connection
import java.sql.DriverManager

class DBConfig {
    fun connect(): Connection {
        val url = "jdbc:postgresql://localhost:5432/postgres"
        // set the username
        val username = "postgres"
        // set the password
        val password = "root"

        return DriverManager.getConnection(url,username,password)

    }
}