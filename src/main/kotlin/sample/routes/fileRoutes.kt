

package sample.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.fileRouting(){
    route("/file"){
        get (){
            val file = File("files/Junsay CV.jpg")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Inline.withParameter(
                    ContentDisposition.Parameters.FileName, "downloadableCV.jpg"
                ).toString()
            )
            call.respondFile(file)
        }
        route("/download"){
            get (){
                val file = File("files/Junsay CV.jpg")
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(
                        ContentDisposition.Parameters.FileName, "downloadableCV.jpg"
                    ).toString()
                )

                call.respondText("Downloading")
            }
        }
    }
}