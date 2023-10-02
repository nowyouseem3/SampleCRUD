package sample.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.controllers.userController
import sample.models.textResponse

fun Route.homeRouting(){
    route("/"){
        get() {
            try {
                val display = userController().test()
                call.respond(display)
            }
            catch (e: Exception){
                return@get call.respond(textResponse("Server Error: $e", 500))
            }

        }
    }

}