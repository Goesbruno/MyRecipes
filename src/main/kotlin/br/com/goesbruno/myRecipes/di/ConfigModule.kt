package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.application.config.AppConfig
import br.com.goesbruno.myRecipes.utils.Constants.DATABASE_NAME
import br.com.goesbruno.myRecipes.utils.Constants.MONGODB_URI_LOCAL
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

object ConfigModule {

    val module = module {
        single<AppConfig> { AppConfig() }
    }

}