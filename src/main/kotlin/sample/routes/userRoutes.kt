package sample.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.models.*
import sample.controllers.userController
import sample.postValidation.*

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

//            call.respond(userNameValidation(username))
            if (userNameValidation(username)){
                call.respondText("User has been added.", status = HttpStatusCode.Created)
                userController().createUser(fullname, address, username, password)
            }
            else{
                call.respondText(" $username Is Not Valid or Available", status = HttpStatusCode.BadRequest)
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

    }

}