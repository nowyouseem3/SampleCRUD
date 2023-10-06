package sample.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.models.*
import sample.controllers.userController
import sample.validation.userPutNameValidation

fun Route.userRouting(){
    route("/user"){
        registerUserRouting()
        userLoginRouting()
        get {
            try {
                // set and calling the getUserData function to fetch returned value
                val userValues = userController().getUserData()
                // display json
                call.respond(HttpStatusCode.OK,userValues)
            }
            catch (e : Exception){
                call.respond(textResponse("DataBase Error: $e", 500))
            }

        }
        get("{id?}") {
            try {
                // set and calling the getUserData function to fetch return value
                val userStorage = userController().getUserData()
                // set and declaring variable with the value from parameters and return error if null
                val id = call.parameters["id"] ?: return@get call.respond(textResponse("Bad Request", 400))
                // declaring variable with the value from params and find the id if there's or not
                val user = userStorage.find { it.id == id.toInt()} ?: return@get call.respond(textResponse("Not Found", 404))
                //otherwise display json
                call.respond(user)
            }
            catch (e : Exception){
                return@get call.respond(textResponse("Server Error: $e", 500))
            }
        }

        delete("{id?}") {
            try {
                // set and calling the getUserData function to fetch returned value
                val userStorage = userController().getUserData()
                // set and declaring variable with the value from parameters and return error if null
                val id = call.parameters["id"] ?: return@delete call.respond(textResponse("Bad Request", 400))
                // find if the said id exist and delete otherwise not exist
                if (userStorage.removeIf { it.id == id.toInt() }) {
                    userController().userDelete(id.toInt())
                    call.respond(textResponse("Accepted", 202))
                } else {
                    call.respond(textResponse("Not Found", 404))
                }
            }
            catch (e: Exception){
                return@delete call.respond(textResponse("Server Error: $e", 500))
            }

        }

        put("{id?}"){
            try {
                // declaring variable that can get all models from putUsers model
                val user = call.receive<putUsers>()
                //destructure user
                val (userEmail, password, fullName , phone, address) = user
                // set and declaring variable with the value from parameters and return error if null
                val declareId = call.parameters["id"]?: return@put call.respond(textResponse("Bad Request", 400))
                // set and calling the getUserData function to fetch return value
                val userStorage = userController().getUserData()
                // check if the id exist
                if (userStorage.removeIf { it.id == declareId.toInt() }) {
                    //validate if the changes meet the requiements and unique from other
                    if (userPutNameValidation(userEmail, declareId.toInt(), fullName)){

                        userController().userUpdate(userEmail, password, fullName , phone, address, declareId.toInt() )
                        call.respond(textResponse("Accepted", 202))
                    }
                    else {
                        call.respond(textResponse("Not Acceptable", 406))
                    }
                } else {
                    call.respond(textResponse("Not Found", 404))
                }
            }
            catch (e : Exception){
                return@put call.respond(textResponse("Server Error: $e", 500))
            }

        }
    }
}