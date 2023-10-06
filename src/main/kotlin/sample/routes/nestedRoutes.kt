package sample.routes


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.controllers.nestedController
import sample.controllers.userController
import sample.models.*
import sample.validation.userValidation

fun Route.nestedRouting(){
    route("/nestedjson"){
        get {
            try {
                // set and calling the getUserData function to fetch returned value
                val userValues = nestedController().getParentData()
                // display json
                call.respond(HttpStatusCode.OK,userValues)
            }
            catch (e : Exception){
                call.respond(textResponse("DataBase Error: $e", 500))
            }
        }
        get("{id?}") {

        }

        delete("{id?}") {

        }

        put("{id?}"){

        }
        post {
            try {
                // declaring variable that can get all models from postUsers model
                val user = call.receive<insertperson>()
                // declaring destructure with the same parameter position from postUser model
                val (fullName,userId,companyName, jobTitle, companyAddress,yearsOfExperiences) = user

                nestedController().insertperson(userId, fullName, companyName, jobTitle, companyAddress, yearsOfExperiences)
                call.respond(textResponse("accepted", 201))
            }
            catch (e: Exception){
                return@post call.respond(textResponse("Server Error: $e", 500))
            }
        }
    }
}