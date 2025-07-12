package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserWriteOnlyRepository
import br.com.goesbruno.myRecipes.utils.Constants.DATABASE_NAME
import br.com.goesbruno.myRecipes.utils.Constants.MONGODB_URI_LOCAL
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

object RepositoryModule {

    val module = module {
        single<UserWriteOnlyRepository> { UserRepository(get())}
        single<UserReadOnlyRepository> { UserRepository(get())}
    }

}