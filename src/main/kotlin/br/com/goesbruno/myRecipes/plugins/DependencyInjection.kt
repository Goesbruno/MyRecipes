package br.com.goesbruno.myRecipes.plugins

import br.com.goesbruno.myRecipes.di.DatabaseModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureDependencyInjection(){

    val modules = DatabaseModule.module

    install(Koin){
        slf4jLogger()
        modules(modules)
    }

}