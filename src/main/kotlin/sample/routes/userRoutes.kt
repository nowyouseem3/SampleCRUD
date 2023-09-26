package sample.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.models.*
import sample.controllers.userController
import sample.validation.userValidation
import sample.validation.userPutNameValidation

fun Route.userRouting(){
    route("/user"){
        get {
            val userValues = userController().getUserData()
            call.respond(userValues)
        }
        get("{id?}") {
            val userStorage = userController().getUserData()
            val id = call.parameters["id"] ?: return@get call.respond(textResponse("Bad Request", 400))
            val user = userStorage.find { it.id == id.toInt() } ?: call.respond(textResponse("Not Found", 404))

            call.respond(user)
        }

        post {
            val user = call.receive<postUsers>()
            val (fullname,username,password,address) = user

            if (userValidation(username,fullname,password)){
                call.respond(textResponse("Created", 201))
                userController().createUser(fullname, address, username, password)
            }
            else{
                call.respond(textResponse("Not Acceptable", 406))
            }

        }

        delete("{id?}") {
            val userStorage = userController().getUserData()
            val id = call.parameters["id"] ?: return@delete call.respond(textResponse("Bad Request", 400))
            if (userStorage.removeIf { it.id == id.toInt() }) {
                call.respond(textResponse("Accepted", 202))
                userController().userDelete(id.toInt())
            } else {
                call.respond(textResponse("Not Found", 404))
            }
        }

        put("{id?}"){
            val user = call.receive<putUsers>()
            //destructure user
            val (fullname,userName,password,address) = user

            val declareId = call.parameters["id"]?: return@put call.respond(textResponse("Bad Request", 400))
            val userStorage = userController().getUserData()
            if (userStorage.removeIf { it.id == declareId.toInt() }) {
                if (userPutNameValidation(userName, declareId.toInt())){
                    call.respond(textResponse("Accepted", 202))
                    userController().userUpdate( "$fullname", "$address ", "$userName","$password", declareId.toInt() )
                }
                else{
                    call.respond(textResponse("Not Acceptable", 406))
                }

            } else {
                call.respond(textResponse("Not Found", 404))
            }
        }

    }

}