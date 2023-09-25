package sample

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import sample.models.getUsers
import sample.plugins.*


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0"){
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}

fun Application.module() {
    configureSecurity()

}