package br.com.goesbruno.myRecipes.plugins

import br.com.goesbruno.myRecipes.application.payloads.responses.ErrorResponse
import br.com.goesbruno.myRecipes.domain.exceptions.UserAuthenticationNotFoundException
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.application.*
import io.ktor.server.response.respond

fun Application.configureStatusPage() {
    install(StatusPages) {

        status(HttpStatusCode.NotFound) { call, cause ->
            val errorResponse = ErrorResponse(
                httpStatusCode = HttpStatusCode.NotFound.value,
                message = cause.description
            )
            call.respond(
                message = errorResponse,
                status = HttpStatusCode.NotFound
            )
        }

        status(HttpStatusCode.Unauthorized) { call, cause ->
            val errorResponse = ErrorResponse(
                httpStatusCode = HttpStatusCode.Unauthorized.value,
                message = cause.description
            )
            call.respond(
                message = errorResponse,
                status = HttpStatusCode.Unauthorized
            )
        }

        status(HttpStatusCode.Conflict) { call, cause ->
            val errorResponse = ErrorResponse(
                httpStatusCode = HttpStatusCode.Conflict.value,
                message = cause.description
            )
            call.respond(
                message = errorResponse,
                status = HttpStatusCode.Conflict
            )
        }

        exception<IllegalArgumentException> { call, cause ->
            val errorResponse = ErrorResponse(
                httpStatusCode = HttpStatusCode.BadRequest.value,
                message = cause.message.orEmpty()
            )
            call.respond(
                message = errorResponse,
                status = HttpStatusCode.BadRequest
            )
        }

        exception<UserAuthenticationNotFoundException> { call, cause ->
            val errorResponse = ErrorResponse(
                httpStatusCode = HttpStatusCode.NotFound.value,
                message = cause.message.orEmpty()
            )
            call.respond(
                message = errorResponse,
                status = HttpStatusCode.NotFound
            )
        }

    }
}