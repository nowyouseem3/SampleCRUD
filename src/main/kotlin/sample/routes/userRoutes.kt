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
            // set and calling the getUserData function to fetch returned value
            val userValues = userController().getUserData()
            // display json
            call.respond(userValues)
        }
        get("{id?}") {
            // set and calling the getUserData function to fetch return value
            val userStorage = userController().getUserData()
            try {
                try {
                    // set and declaring variable with the value from parameters and return error if null
                    val id = call.parameters["id"]
                    // declaring variable with the value from params and find the id if there's or not
                    val user = userStorage.find { it.id == id!!.toInt()} ?: return@get call.respond(textResponse("Not Found", 404))
                    //otherwise display json
                    call.respond(user)
                }
                catch (e: Exception){
                    return@get call.respond(textResponse("Bad Request: $e", 400))
                }
            }
            catch (e : Exception){
                return@get call.respond(textResponse("Server Error: $e", 500))
            }
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

        delete("{id?}") {
            // set and calling the getUserData function to fetch returned value
            val userStorage = userController().getUserData()
            // set and declaring variable with the value from parameters and return error if null
            val id = call.parameters["id"] ?: return@delete call.respond(textResponse("Bad Request", 400))
            // find if the said id exist and delete otherwise not exist
            if (userStorage.removeIf { it.id == id.toInt() }) {
                call.respond(textResponse("Accepted", 202))
                userController().userDelete(id.toInt())
            } else {
                call.respond(textResponse("Not Found", 404))
            }
        }

        put("{id?}"){
            // declaring variable that can get all models from putUsers model
            val user = call.receive<putUsers>()
            //destructure user
            val (fullname,userName,password,address) = user
            // set and declaring variable with the value from parameters and return error if null
            val declareId = call.parameters["id"]?: return@put call.respond(textResponse("Bad Request", 400))
            // set and calling the getUserData function to fetch return value
            val userStorage = userController().getUserData()
            // check if the id exist
            if (userStorage.removeIf { it.id == declareId.toInt() }) {
                //validate if the changes meet the requiements and unique from other
                if (userPutNameValidation(userName, declareId.toInt())){
                    call.respond(textResponse("Accepted", 202))
                    userController().userUpdate( "$fullname", "$address ", "$userName","$password", declareId.toInt() )
                }
                else {
                    call.respond(textResponse("Not Acceptable", 406))
                }
            } else {
                call.respond(textResponse("Not Found", 404))
            }
        }

    }

}