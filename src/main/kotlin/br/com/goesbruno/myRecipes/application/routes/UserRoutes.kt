package br.com.goesbruno.myRecipes.application.routes

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUserRequest
import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest
import br.com.goesbruno.myRecipes.domain.entity.User
import br.com.goesbruno.myRecipes.domain.extensions.getUserAuthentication
import br.com.goesbruno.myRecipes.domain.services.user.AddUserService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserProfileService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
import br.com.goesbruno.myRecipes.utils.Constants
import br.com.goesbruno.myRecipes.utils.ErrorCodes

import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.usersRoute(
    addUserService: AddUserService,
    loginUserService: LoginUserService,
    getUserProfileService: GetUserProfileService
) {
    route(Constants.USER_ROUTE) {
        createUser(addUserService)
        loginUser(loginUserService)

        authenticate {
            getUserProfile(getUserProfileService)
        }
    }
}

fun Route.loginUser(loginUserService: LoginUserService) {
    post("/login") {
        try {
            val request = call.receiveNullable<AuthUserRequest>()
            if (request != null) {
                val simpleResponse = loginUserService.loginUser(request)
                if (simpleResponse.successful) {
                    call.respond(HttpStatusCode.OK, simpleResponse)
                } else {
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


fun Route.createUser(addUserService: AddUserService) {
    post("/register") {
        try {
            val request = call.receiveNullable<AddUserRequest>()
            if (request != null) {
                val simpleResponse = addUserService.addUser(request)
                if (simpleResponse.successful) {
                    call.respond(HttpStatusCode.Created, simpleResponse)
                } else {
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


fun Route.getUserProfile(getUserProfileService: GetUserProfileService) {

    get("/profile") {
        try {

            val userId = call.getUserAuthentication()
            val userResponse = getUserProfileService.getUserProfileById(userId)
            call.respond(userResponse)

        } catch (e: ServerResponseException) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}