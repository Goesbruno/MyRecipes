package br.com.goesbruno.myRecipes.domain.services.token

import br.com.goesbruno.myRecipes.application.config.AppConfig
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException

class TokenService (
    appConfig: AppConfig
) {

    private val issuer = appConfig.applicationConfig.property("jwt.issuer").getString()
    private val realm = appConfig.applicationConfig.property("jwt.realm").getString()
    private val audience = appConfig.applicationConfig.property("jwt.audience").getString()
    private val secret = appConfig.applicationConfig.property("jwt.secret").getString()
    private val algorithm = Algorithm.HMAC256(secret)

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