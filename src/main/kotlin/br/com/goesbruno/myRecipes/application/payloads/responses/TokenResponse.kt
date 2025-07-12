package br.com.goesbruno.myRecipes.application.payloads.responses

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("successful")
    val successful: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("token")
    val token: String? = ""
)
