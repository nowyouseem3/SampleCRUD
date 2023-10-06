package sample.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.routes.*

fun Application.configureRouting() {
    routing {
        userRouting()
        homeRouting()
        fileRouting()
        nestedRouting()
    }
}
