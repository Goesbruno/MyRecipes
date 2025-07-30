package br.com.goesbruno.myRecipes.domain.services.recipe

import br.com.goesbruno.myRecipes.application.mappers.toRecipeResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.RecipeResponse
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeReadOnlyRepository

class FindUserRecipesService(
    private val recipesReadOnlyRepository: RecipeReadOnlyRepository
) {

    suspend fun search(nameOrIngredient: String, userId: String): List<RecipeResponse> {

        if (nameOrIngredient.isNotEmpty() || nameOrIngredient.isNotBlank()) {
            val recipes = recipesReadOnlyRepository.search(nameOrIngredient, userId)
            return recipes.map { it.toRecipeResponse() }
        } else {
            return emptyList()
        }
    }
}