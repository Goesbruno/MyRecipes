package br.com.goesbruno.myRecipes.domain.services.token

import br.com.goesbruno.myRecipes.utils.Constants
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException

class TokenService {

    private val issuer = "MyRecipesServer"
    private val realm = "project.recipes"
    private val audience = "MyRecipesApp"
    private val jwtSecret = System.getenv(Constants.SECRET)
    private val algorithm = Algorithm.HMAC256(jwtSecret)

    private val verifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    fun realm() = realm
    fun audience() = audience
    fun verifier(): JWTVerifier = verifier


    fun generateToken(userId: String): String {
        return try {
            JWT.create()
                .withSubject(userId)
                .withAudience(audience)
                .withIssuer(issuer)
                .sign(algorithm)

        } catch (e: JWTVerificationException) {
            throw IllegalArgumentException(ErrorCodes.TOKEN_GENERATION_ERROR.message)
        }
    }
}