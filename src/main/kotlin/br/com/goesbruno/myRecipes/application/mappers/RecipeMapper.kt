package br.com.goesbruno.myRecipes.application.mappers

import br.com.goesbruno.myRecipes.application.payloads.requests.IngredientRequest
import br.com.goesbruno.myRecipes.domain.entity.Ingredient
import org.bson.types.ObjectId

fun IngredientRequest.toIngredient(recipeId: String) = Ingredient(
    name = name,
    quantity = quantity,
    recipeId = recipeId,
    id = ObjectId.get().toHexString()
)