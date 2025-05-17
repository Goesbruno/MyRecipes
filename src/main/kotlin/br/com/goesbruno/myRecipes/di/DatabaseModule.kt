package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.utils.Constants.DATABASE_NAME
import br.com.goesbruno.myRecipes.utils.Constants.MONGODB_URI_LOCAL
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

object DatabaseModule {

    val module = module {
        single {
            val client = MongoClient.create(System.getenv(MONGODB_URI_LOCAL))
            client.getDatabase(System.getenv(DATABASE_NAME))
        }
    }

}