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

                // declaring variable that can get all models from postUsers model
                val user = call.receive<postUsers>()
                // declaring destructure with the same parameter position from postUser model
                val (fullname,username,password,address) = user
                // calling the userValidation to identify if the setting store data are meet the requirements
                if (userValidation(username,fullname,password)){
                    call.respond(textResponse("Created", 201))
                    userController().createUser(fullname, address, username, password)
                }
                else{
                    call.respond(textResponse("Not Acceptable", 406))
                }



        }
    }
}