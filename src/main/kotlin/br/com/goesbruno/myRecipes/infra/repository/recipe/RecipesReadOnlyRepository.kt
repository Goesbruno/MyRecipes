package br.com.goesbruno.myRecipes.infra.repository.recipe

import br.com.goesbruno.myRecipes.domain.entity.CategoryEnum
import br.com.goesbruno.myRecipes.domain.entity.Recipe

interface RecipesReadOnlyRepository {
    suspend fun getByUser(userId: String, categoryEnum: CategoryEnum?): List<Recipe>
    suspend fun getById(recipeId: String, userId: String): Recipe?
    suspend fun search(nameOrIngredients: String, userId: String): List<Recipe>
    suspend fun checkIfExists(recipeId: String): Boolean
}