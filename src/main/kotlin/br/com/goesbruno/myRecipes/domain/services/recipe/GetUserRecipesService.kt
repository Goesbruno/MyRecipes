package br.com.goesbruno.myRecipes.domain.services.recipe

import br.com.goesbruno.myRecipes.application.mappers.toRecipeResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.RecipeResponse
import br.com.goesbruno.myRecipes.domain.entity.CategoryEnum
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeReadOnlyRepository

class GetUserRecipesService(
    private val recipesReadOnlyRepository: RecipeReadOnlyRepository
) {

    suspend fun getAll(userId: String, category: Int?): List<RecipeResponse> {

        val recipes = recipesReadOnlyRepository.getByUser(
            userId = userId,
            categoryEnum = category?.let { CategoryEnum.fromInt(it) }
        )

        return recipes.map { it.toRecipeResponse() }

    }

}