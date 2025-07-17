package br.com.goesbruno.myRecipes.domain.validations

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes


interface RecipeRequestValidation {
    suspend fun validator(request: AddUpdateRecipeRequest, userId: String?): SimpleResponse
}

class RecipeRequestValidationImpl : RecipeRequestValidation{
    override suspend fun validator(
        request: AddUpdateRecipeRequest,
        userId: String?
    ): SimpleResponse {
        return when {
            userId.isNullOrEmpty() -> SimpleResponse (
                successful = false,
                message = ErrorCodes.USER_ID_NOT_FOUND.message
            )

            request.name.isEmpty() -> SimpleResponse (
                successful = false,
                message = ErrorCodes.TITLE_REQUIRED.message
            )

            request.category < 0 -> SimpleResponse (
                successful = false,
                message = ErrorCodes.CATEGORY_REQUIRED.message
            )

            request.preparationTime.isEmpty() -> SimpleResponse (
                successful = false,
                message = ErrorCodes.PREPARATION_TIME_REQUIRED.message
            )

            request.preparationMode.isEmpty() -> SimpleResponse (
                successful = false,
                message = ErrorCodes.PREPARATION_MODE_REQUIRED.message
            )

            request.ingredients.isEmpty() -> SimpleResponse (
                successful = false,
                message = ErrorCodes.INGREDIENTS_REQUIRED.message
            )

            else -> SimpleResponse (
                successful = true,
                message = SuccessCodes.VALID_REGISTRATION.message
            )
        }
    }
}