package sample.routes


import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.models.*
import sample.controllers.userController
import sample.validation.userValidation

fun Route.registerUserRouting(){
    route("/registration"){
        get{
            call.respond("hello register")
        }
        post {
            try {
                // declaring variable that can get all models from postUsers model
                val user = call.receive<PostUsers>()
                // declaring destructure with the same parameter position from postUser model
                val (userID, userEmail, password, fullname, phone, address) = user
                // calling the userValidation to identify if the setting store data are meet the requirements
                if (!userValidation(userEmail,fullname,password)) return@post call.respond(textResponse("Failed", 406))
                else
                    userController().createUser(userID, userEmail, password, fullname, phone, address)
                    call.respond(textResponse("Success", 200))
            }
            catch (e: Exception){
                return@post call.respond(textResponse("Server Error: $e", 500))
            }
        }
    }
}