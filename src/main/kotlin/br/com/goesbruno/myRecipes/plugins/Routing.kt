package br.com.goesbruno.myRecipes.plugins

import br.com.goesbruno.myRecipes.application.routes.usersRoute
import br.com.goesbruno.myRecipes.domain.services.user.AddUserService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
import io.ktor.server.application.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val addUserService by inject<AddUserService>()
    val loginUserService by inject<LoginUserService>()

    install(Routing){
        usersRoute(addUserService, loginUserService)
    }
}
