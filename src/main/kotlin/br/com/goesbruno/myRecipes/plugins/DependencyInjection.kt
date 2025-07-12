package br.com.goesbruno.myRecipes.plugins

import br.com.goesbruno.myRecipes.di.*
import br.com.goesbruno.myRecipes.domain.database.DatabaseService
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureDependencyInjection() {

    val modules = DatabaseModule.module +
            RepositoryModule.module +
            ServiceModule.module +
            ValidationsModule.module +
            ConfigModule.module

    install(Koin) {
        slf4jLogger()
        modules(modules)
    }

    val databaseService by inject<DatabaseService>()
    environment.monitor.subscribe(ApplicationStopped){
        databaseService.close()
    }

}