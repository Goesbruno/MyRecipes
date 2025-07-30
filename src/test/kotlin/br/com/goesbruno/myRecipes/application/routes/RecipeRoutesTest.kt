package br.com.goesbruno.myRecipes.application.routes

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequestFactory
import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.RecipeResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.TokenResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.Test

class RecipeRoutesTest {

    private val applicationConfig = ApplicationConfig("application.conf")
    private val authUserRequest = AuthUserRequest(
        email = "testezao@email.com.br",
        password = "1234567"
    )
    private val addRecipeRequest = AddUpdateRecipeRequestFactory().create()
    private val updateRecipeRequest = AddUpdateRecipeRequestFactory().update()

    @Test
    fun create() = testApplication {

        environment { applicationConfig }

        var client = createClient {
            install(ContentNegotiation){
                gson()
            }
        }

        val responseLogin = client.post("api/v1/users/login"){
            contentType(ContentType.Application.Json)
            setBody(authUserRequest)
        }
        val tokenResponse = responseLogin.body<TokenResponse>()

        client = createClient {
            install(ContentNegotiation){
                gson()
            }
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens(tokenResponse.token.orEmpty(), tokenResponse.token.orEmpty())
                    }
                }
            }
        }

        val response = client.post("api/v1/recipes") {
            contentType(ContentType.Application.Json)
            setBody(addRecipeRequest)
        }

        val responseBody = response.body<SimpleResponse>()

        assertThat(response.status).isEqualTo(HttpStatusCode.Created)
        assertThat(responseBody.successful).isTrue()

    }

    @Test
    fun getAll() = testApplication {

        environment { applicationConfig }

        var client = createClient {
            install(ContentNegotiation){
                gson()
            }
        }

        val responseLogin = client.post("api/v1/users/login"){
            contentType(ContentType.Application.Json)
            setBody(authUserRequest)
        }
        val tokenResponse = responseLogin.body<TokenResponse>()

        client = createClient {
            install(ContentNegotiation){
                gson()
            }
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens(tokenResponse.token.orEmpty(), tokenResponse.token.orEmpty())
                    }
                }
            }
        }

        val response = client.get("api/v1/recipes") {
            contentType(ContentType.Application.Json)
        }

        val responseText = response.bodyAsText()
        println("ResponseText: $responseText")

        val responseBody = response.body<List<RecipeResponse>>()


        assertThat(responseBody).isNotEmpty()

    }

    @Test
    fun search() = testApplication {

        environment { applicationConfig }

        var client = createClient {
            install(ContentNegotiation){
                gson()
            }
        }

        val responseLogin = client.post("api/v1/users/login"){
            contentType(ContentType.Application.Json)
            setBody(authUserRequest)
        }
        val tokenResponse = responseLogin.body<TokenResponse>()

        client = createClient {
            install(ContentNegotiation){
                gson()
            }
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens(tokenResponse.token.orEmpty(), tokenResponse.token.orEmpty())
                    }
                }
            }
        }

        val response = client.get("api/v1/recipes/search") {
            contentType(ContentType.Application.Json)
            parameter("nameOrIngredient", "lasa")
        }

        val responseText = response.bodyAsText()
        println("ResponseText: $responseText")

        val responseBody = response.body<List<RecipeResponse>>()


        assertThat(responseBody).isNotEmpty()

    }

}