package br.com.goesbruno.myRecipes.plugins

import br.com.goesbruno.myRecipes.application.routes.usersRoute
import br.com.goesbruno.myRecipes.domain.services.user.RegisterUserService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserProfileService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
import io.ktor.server.application.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val registerUserService by inject<RegisterUserService>()
    val loginUserService by inject<LoginUserService>()
    val getUserProfileService by inject<GetUserProfileService>()

    install(Routing){
        usersRoute(registerUserService, loginUserService, getUserProfileService)
    }
}
