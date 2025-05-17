package br.com.goesbruno.myRecipes

import br.com.goesbruno.myRecipes.plugins.configureDependencyInjection
import br.com.goesbruno.myRecipes.plugins.configureHTTP
import br.com.goesbruno.myRecipes.plugins.configureMonitoring
import br.com.goesbruno.myRecipes.plugins.configureRouting
import br.com.goesbruno.myRecipes.plugins.configureSecurity
import br.com.goesbruno.myRecipes.plugins.configureSerialization
import br.com.goesbruno.myRecipes.plugins.configureSockets
import br.com.goesbruno.myRecipes.plugins.configureStatusPage
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureDependencyInjection()
    configureSecurity()
    configureHTTP()
    configureStatusPage()
    configureMonitoring()
    configureSerialization()
    configureSockets()
    configureRouting()
}
