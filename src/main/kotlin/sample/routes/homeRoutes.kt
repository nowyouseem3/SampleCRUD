package sample.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.homeRouting(){
    route("/"){
        get() {
            call.respondText("home")
        }
    }

}