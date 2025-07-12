package br.com.goesbruno.br.com.goesbruno.myRecipes.application.routes

import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest
import br.com.goesbruno.myRecipes.application.payloads.requests.RegisterUserRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.TokenResponse
import br.com.goesbruno.myRecipes.application.payloads.responses.UserResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.testApplication
import kotlin.test.Test

class UserRoutesTest {

    private val applicationConfig = ApplicationConfig("application.conf")
    private val authUserRequest = AuthUserRequest(
        email = "testezao@email.com.br",
        password = "1234567"
    )
    private val userRequest = RegisterUserRequest(
        name = "Teste",
        email = "testezao@email.com.br",
        password = "1234567",
        phone = "12 3 4567-8910"
    )

    @Test
    fun register() = testApplication {

        environment { applicationConfig }

        val client = createClient {
            install(ContentNegotiation){
                gson()
            }
        }

        val response = client.post("api/v1/users/register"){
            contentType(ContentType.Application.Json)
            setBody(userRequest)
        }
        val simpleResponse = response.body<SimpleResponse>()

        println("SimpleResponse: $simpleResponse")
        assertThat(response.status).isEqualTo(HttpStatusCode.Created)
        assertThat(simpleResponse.successful).isTrue()
        assertThat(simpleResponse.message).isNotEmpty()
    }


    @Test
    fun login() = testApplication {
        environment { applicationConfig }

        val client = createClient {
            install(ContentNegotiation){
                gson()
            }
        }

        val response = client.post("api/v1/users/login"){
            contentType(ContentType.Application.Json)
            setBody(authUserRequest)
        }
        val tokenResponse = response.body<TokenResponse>()

        println("TokenResponse: $tokenResponse")
        assertThat(tokenResponse.successful).isTrue()
        assertThat(tokenResponse.token).isNotEmpty()
        assertThat(tokenResponse.message).isNotEmpty()
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)

    }

    @Test
    fun getProfile() = testApplication {
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

        val response = client.get("api/v1/users/profile"){
            contentType(ContentType.Application.Json)
        }
        val userResponse = response.body<UserResponse>()

        println("UserResponse: $userResponse")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
        assertThat(userResponse).isNotNull()

    }
}