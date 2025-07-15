package br.com.goesbruno.myRecipes.infra.repository.recipe

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequest
import br.com.goesbruno.myRecipes.domain.entity.Recipe

interface RecipesWriteOnlyRepository {
    suspend fun create(recipe: Recipe): Boolean
    suspend fun remove(recipeId: String): Boolean
    suspend fun update(recipeId: String, addUpdateRecipeRequest: AddUpdateRecipeRequest): Boolean
}