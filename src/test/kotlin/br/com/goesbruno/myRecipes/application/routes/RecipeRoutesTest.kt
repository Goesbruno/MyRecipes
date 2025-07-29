package br.com.goesbruno.myRecipes.application.routes

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequestFactory
import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.RecipeResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.TokenResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import io.ktor.server.config.*
import io.ktor.server.testing.testApplication
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

}