package br.com.goesbruno.myRecipes.application.routes

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUserRequest
import br.com.goesbruno.myRecipes.domain.services.user.AddUserService
import br.com.goesbruno.myRecipes.utils.Constants
import br.com.goesbruno.myRecipes.utils.ErrorCodes

import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.usersRoute(
    addUserService: AddUserService
){
    route(Constants.USER_ROUTE){
        createUser(addUserService)
    }
}


fun Route.createUser(addUserService: AddUserService){
    post("/register") {
        try {
            val request = call.receiveNullable<AddUserRequest>()
            if (request != null) {
                val simpleResponse = addUserService.addUser(request)
                if (simpleResponse.successful){
                    call.respond(HttpStatusCode.Created, simpleResponse)
                } else  {
                    call.respond(HttpStatusCode.BadRequest, simpleResponse)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, ErrorCodes.UNKNOWN_ERROR.message)
            }

        } catch (e: ServerResponseException) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}