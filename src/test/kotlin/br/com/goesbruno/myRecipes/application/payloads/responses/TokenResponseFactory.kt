package br.com.goesbruno.br.com.goesbruno.myRecipes.application.payloads.responses

import br.com.goesbruno.myRecipes.application.payloads.responses.TokenResponse

class TokenResponseFactory {

    fun create(successful: Boolean, message: String, token: String): TokenResponse {
        return TokenResponse(successful,message,token)
    }

}