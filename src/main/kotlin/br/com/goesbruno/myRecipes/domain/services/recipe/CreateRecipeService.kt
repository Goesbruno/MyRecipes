package br.com.goesbruno.myRecipes.domain.services.recipe

import br.com.goesbruno.myRecipes.application.mappers.toIngredient
import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.domain.entity.CategoryEnum
import br.com.goesbruno.myRecipes.domain.entity.Recipe
import br.com.goesbruno.myRecipes.domain.validations.RecipeRequestValidation
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeWriteOnlyRepository
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes
import org.bson.types.ObjectId

class CreateRecipeService(
    private val recipeRequestValidation: RecipeRequestValidation,
    private val recipeWriteOnlyRepository: RecipeWriteOnlyRepository
) {

    suspend fun create(request: AddUpdateRecipeRequest, userId: String): SimpleResponse {

        val validator = recipeRequestValidation.validator(request = request, userId = userId)
        if (!validator.successful) {
            return validator
        }

        val recipeId = ObjectId.get().toHexString()
        val recipe = Recipe(
            id = recipeId,
            name = request.name,
            userId = userId,
            category = CategoryEnum.fromInt(request.category),
            preparationMode = request.preparationMode,
            preparationTime = request.preparationTime,
            ingredients = request.ingredients.map {
                it.toIngredient(recipeId)
            },
        )

        val result = recipeWriteOnlyRepository.create(recipe)
        return if (result) {
            SimpleResponse(
                successful = true,
                message = SuccessCodes.REGISTRATION_COMPLETED.message
            )
        } else {
            SimpleResponse(
                successful = false,
                message = ErrorCodes.RECIPE_REGISTRATION_ERROR.message
            )
        }
    }

}