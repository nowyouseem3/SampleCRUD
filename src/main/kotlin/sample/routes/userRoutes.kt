package sample.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.models.*
import sample.controllers.userController
import sample.validation.postValidation.userNameValidation
import sample.validation.putValidation.userPutNameValidation

fun Route.userRouting(){
    route("/user"){
        get {
            val userValues = userController().getUserData()
            call.respond(userValues)
        }
        get("{id?}") {
            val userStorage = userController().getUserData()
            val id = call.parameters["id"] ?: return@get call.respondText("Missing ID!", status = HttpStatusCode.BadRequest)
            val user = userStorage.find { it.id == id.toInt() } ?: call.respondText("No User ID found!", status = HttpStatusCode.NotFound)

            call.respond(user)
        }

        post {
            val user = call.receive<postUsers>()
            val (fullname,username,password,address) = user

            if (userNameValidation(username)){
                call.respondText("User has been added.", status = HttpStatusCode.Created)
                userController().createUser(fullname, address, username, password)
            }
            else{
                call.respondText(" $username Is Not Valid or Available", status = HttpStatusCode.NotAcceptable)
            }

        }

        delete("{id?}") {
            val userStorage = userController().getUserData()
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (userStorage.removeIf { it.id == id.toInt() }) {
                call.respondText(" $id was removed correctly", status = HttpStatusCode.Accepted)
                userController().userDelete(id)
            } else {
                call.respondText(" $id Id Not Found", status = HttpStatusCode.NotFound)
            }
        }

        put("{id?}"){
            val user = call.receive<putUsers>()
            //destructure user
            val (fullname,userName,password,address) = user

            val declareId = call.parameters["id"]?: return@put call.respond(HttpStatusCode.BadRequest)
            val userStorage = userController().getUserData()
            if (userStorage.removeIf { it.id == declareId.toInt() }) {
                if (userPutNameValidation(userName, declareId.toInt())){
                    call.respondText(" $declareId was updated correctly", status = HttpStatusCode.Accepted)
                    userController().userUpdate( "$fullname", "$address ", "$userName","$password", declareId.toInt() )
                }
                else{
                    call.respondText(" $userName Is Not Valid or Available", status = HttpStatusCode.OK)
                }

            } else {
                call.respondText(" $declareId Id Not Found", status = HttpStatusCode.NotFound)
            }
        }

    }

}