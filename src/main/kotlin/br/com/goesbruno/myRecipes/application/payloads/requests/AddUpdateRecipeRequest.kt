package br.com.goesbruno.myRecipes.application.payloads.requests

import br.com.goesbruno.myRecipes.domain.entity.Ingredient
import com.google.gson.annotations.SerializedName

data class AddUpdateRecipeRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: Int,
    @SerializedName("preparationMode")
    val preparationMode: String,
    @SerializedName("preparationTime")
    val preparationTime: String,
    @SerializedName("ingredients")
    val ingredients: List<IngredientRequest> = listOf()
)
