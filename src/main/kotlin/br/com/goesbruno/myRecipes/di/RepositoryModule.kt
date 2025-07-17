package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeRepository
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeReadOnlyRepository
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeWriteOnlyRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserWriteOnlyRepository
import org.koin.dsl.module

object RepositoryModule {

    val module = module {
        single<UserWriteOnlyRepository> { UserRepository(get())}
        single<UserReadOnlyRepository> { UserRepository(get())}

        single<RecipeWriteOnlyRepository> { RecipeRepository(get())}
        single<RecipeReadOnlyRepository> { RecipeRepository(get())}
    }

}