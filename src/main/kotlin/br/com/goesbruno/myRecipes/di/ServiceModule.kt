package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.domain.services.password.BCryptPasswordService
import br.com.goesbruno.myRecipes.domain.services.recipe.CreateRecipeService
import br.com.goesbruno.myRecipes.domain.services.recipe.FindUserRecipesService
import br.com.goesbruno.myRecipes.domain.services.recipe.GetUserRecipesService
import br.com.goesbruno.myRecipes.domain.services.token.TokenService
import br.com.goesbruno.myRecipes.domain.services.user.RegisterUserService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserByIdService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserProfileService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
import org.koin.dsl.module

object ServiceModule {

    val module = module {
        single<RegisterUserService> {
            RegisterUserService(
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


        single<TokenService> { TokenService(get()) }
        single<BCryptPasswordService> { BCryptPasswordService() }

        single<CreateRecipeService> { CreateRecipeService(get(), get()) }
        single<GetUserRecipesService> { GetUserRecipesService(get()) }
        single<FindUserRecipesService> { FindUserRecipesService(get()) }
    }

}