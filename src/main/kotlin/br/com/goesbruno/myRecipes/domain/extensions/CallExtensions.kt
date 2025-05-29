package br.com.goesbruno.myRecipes.domain.extensions

import br.com.goesbruno.myRecipes.domain.entity.User
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import io.ktor.server.plugins.NotFoundException

fun ApplicationCall.getUserAuthentication(): String {
    val userModel = authentication.principal<User>()
    if (userModel != null) {
        return userModel.id
    } else {
        throw NotFoundException("Não foi possível obter o usuário logado")
    }
}