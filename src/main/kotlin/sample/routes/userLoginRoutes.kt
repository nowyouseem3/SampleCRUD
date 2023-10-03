package sample.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.models.*
import sample.validation.loginValidation

fun Route.userLoginRouting(){
    route("/login"){
        get{
            call.respond("hello login")
        }
        post {
            try {
                // declaring variable that can get all models from postUsers model
                val user = call.receive<getUserLogin>()
                // declaring destructure with the same parameter position from getUserModel model
                val (username,password) = user
                if (loginValidation(username,password)) return@post call.respond(textResponse("Not Accepted" ,406))
                    else
                    return@post call.respond(textResponse("Accepted" ,202))
            }
            catch (e: Exception){
                call.respond(textResponse("Server Error: $e", 500))
            }
        }
    }
}