package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.domain.services.password.BCryptPasswordService
import br.com.goesbruno.myRecipes.domain.services.user.AddUserService
import org.koin.dsl.module

object ServiceModule {

    val module = module {
        single<AddUserService> {
            AddUserService(
                get(),
                get(),
                get(),
                get()
            )
        }
        single<BCryptPasswordService> { BCryptPasswordService() }
    }

}