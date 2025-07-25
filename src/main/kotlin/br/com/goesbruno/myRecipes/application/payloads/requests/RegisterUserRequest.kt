package br.com.goesbruno.myRecipes.application.payloads.requests

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)