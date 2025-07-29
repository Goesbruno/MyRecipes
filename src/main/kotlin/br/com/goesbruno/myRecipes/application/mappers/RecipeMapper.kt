package br.com.goesbruno.myRecipes.application.mappers

import br.com.goesbruno.myRecipes.application.payloads.requests.IngredientRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.RecipeResponse
import br.com.goesbruno.myRecipes.domain.entity.Ingredient
import br.com.goesbruno.myRecipes.domain.entity.Recipe
import org.bson.types.ObjectId

fun IngredientRequest.toIngredient(recipeId: String) = Ingredient(
    name = name,
    quantity = quantity,
    recipeId = recipeId,
    id = ObjectId.get().toHexString()
)

fun Recipe.toRecipeResponse() = RecipeResponse(
    id = id,
    name = name,
    category = category.description,
    totalIngredients = ingredients.size,
    preparationTime = preparationTime
)