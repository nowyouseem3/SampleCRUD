package sample

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import sample.plugins.*

class ApplicationTest {
    @Test
    fun userTest() = testApplication {
        application {
            configureRouting()
        }
        val response = client.get("/user/2301")
        val resBody = "{"+
                "\"id\":2301," +
                "\"userEmail\":\"sidrick@umtc.try.com\"," +
                "\"userPassword\":\"\$2a\$10\$2WD5iPZGvs1sxIpUJP0p6O7O90CK4lMtQTVfHfUTq7OSCjC8eEcFu\"," +
                "\"userFullName\":\"Sidrick Junsay\"," +
                "\"userPhone\":\"0912345678\"," +
                "\"userAddress\":\"Manila,\"" +
                "}"

        assertEquals(resBody, response.bodyAsText())
    }
}
