package br.com.goesbruno.myRecipes.plugins

import br.com.goesbruno.myRecipes.di.DatabaseModule
import br.com.goesbruno.myRecipes.di.RepositoryModule
import br.com.goesbruno.myRecipes.di.ServiceModule
import br.com.goesbruno.myRecipes.di.ValidationsModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureDependencyInjection() {

    val modules = DatabaseModule.module +
            RepositoryModule.module +
            ServiceModule.module +
            ValidationsModule.module

    install(Koin) {
        slf4jLogger()
        modules(modules)
    }

}