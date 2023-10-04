package sample.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.network.sockets.*
import java.sql.SQLException
import java.sql.Connection



class DBConfig {
    val config = HikariConfig()
    var ds: HikariDataSource? = null

    @Throws(SQLException::class)
    fun connect(): Connection
    {
        config.setJdbcUrl("jdbc:postgresql://192.168.4.134:6432/postgres")
        config.username = "postgres"
        config.password = "root201"
        config.addDataSourceProperty("cachePrepStmts", "true")
        config.addDataSourceProperty("prepStmtCacheSize", "250")
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        ds = HikariDataSource(config)

        return  ds!!.getConnection()
    }

}

/*
class DBConfig {
    fun connect(): Connection {
        val url = "jdbc:postgresql://localhost:5432/postgres"
        // set the username
        val username = "postgres"
        // set the password
        val password = "root201"

        return DriverManager.getConnection(url,username,password)
    }
}*/
