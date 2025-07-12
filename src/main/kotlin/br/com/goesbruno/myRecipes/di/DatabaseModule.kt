package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.domain.database.DatabaseService
import br.com.goesbruno.myRecipes.utils.Constants.DATABASE_NAME
import br.com.goesbruno.myRecipes.utils.Constants.MONGODB_URI_LOCAL
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

object DatabaseModule {

    val module = module {
        single { DatabaseService(get()) }
    }

}