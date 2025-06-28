package br.com.goesbruno.br.com.goesbruno.myRecipes.application.payloads.responses

import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest

class AuthRequestFactory {

    fun create(email: String, password: String) : AuthUserRequest {
        return AuthUserRequest(email,password)
    }


}