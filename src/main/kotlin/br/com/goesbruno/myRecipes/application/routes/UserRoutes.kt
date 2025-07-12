package br.com.goesbruno.myRecipes.application.routes


import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest
import br.com.goesbruno.myRecipes.application.payloads.requests.RegisterUserRequest
import br.com.goesbruno.myRecipes.domain.extensions.getUserAuthentication
import br.com.goesbruno.myRecipes.domain.services.user.GetUserProfileService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
import br.com.goesbruno.myRecipes.domain.services.user.RegisterUserService
import br.com.goesbruno.myRecipes.utils.Constants
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.usersRoute(
    registerUserService: RegisterUserService,
    loginUserService: LoginUserService,
    getUserProfileService: GetUserProfileService
) {
    route(Constants.USER_ROUTE) {
        register(registerUserService)
        login(loginUserService)

        authenticate {
            getProfile(getUserProfileService)
        }
    }
}

fun Route.login(loginUserService: LoginUserService) {
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


fun Route.register(registerUserService: RegisterUserService) {
    post("/register") {
        try {
            val request = call.receiveNullable<RegisterUserRequest>()
            if (request != null) {
                val simpleResponse = registerUserService.register(request)
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


fun Route.getProfile(getUserProfileService: GetUserProfileService) {

    get("/profile") {
        try {

            val userId = call.getUserAuthentication()
            val userResponse = getUserProfileService.getUserProfileById(userId)
            if (userResponse != null) {
                call.respond(userResponse)
            } else {
                call.respond(HttpStatusCode.BadRequest, ErrorCodes.UNKNOWN_ERROR.message)
            }

        } catch (e: ServerResponseException) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }

}