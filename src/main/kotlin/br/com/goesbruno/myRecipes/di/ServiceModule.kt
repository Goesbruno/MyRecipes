package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.domain.services.password.BCryptPasswordService
import br.com.goesbruno.myRecipes.domain.services.token.TokenService
import br.com.goesbruno.myRecipes.domain.services.user.AddUserService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserByIdService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserProfileService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
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
        single<LoginUserService> { LoginUserService(
            get(),
            get(),
            get(),
            get()
        ) }

        single<GetUserProfileService>{ GetUserProfileService(get()) }
        single<GetUserByIdService>{ GetUserByIdService(get()) }


        single<TokenService> { TokenService() }
        single<BCryptPasswordService> { BCryptPasswordService() }
    }

}