package br.com.goesbruno.myRecipes.domain.extensions

import br.com.goesbruno.myRecipes.domain.entity.User
import br.com.goesbruno.myRecipes.domain.exceptions.UserAuthenticationNotFoundException
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun ApplicationCall.getUserAuthentication(): String {
    val userModel = authentication.principal<User>()
    if (userModel != null) {
        return userModel.id
    } else {
        throw UserAuthenticationNotFoundException(ErrorCodes.USER_NOT_LOGGED_IN.message)
    }
}