package br.com.goesbruno.myRecipes.application.payloads.requests

import com.google.gson.annotations.SerializedName

data class IngredientRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: String
)
