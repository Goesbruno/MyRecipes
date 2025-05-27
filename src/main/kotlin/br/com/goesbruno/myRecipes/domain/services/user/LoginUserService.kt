package br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.myRecipes.application.payloads.requests.AuthUserRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.TokenResponse
import br.com.goesbruno.myRecipes.domain.services.password.BCryptPasswordService
import br.com.goesbruno.myRecipes.domain.services.token.TokenService
import br.com.goesbruno.myRecipes.domain.validations.AuthUserRequestValidation
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes

class LoginUserService(
    private val tokenService: TokenService,
    private val authUserRequestValidation: AuthUserRequestValidation,
    private val bCryptPasswordService: BCryptPasswordService,
    private val userReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun loginUser(request: AuthUserRequest): TokenResponse {

        val tokenResponse = authUserRequestValidation.validator(request)

        if (!tokenResponse.successful){
            return tokenResponse
        }

        val userModel = userReadOnlyRepository.checkIfUserExistsReturn(request.email)
            ?: return TokenResponse(successful = false, message = ErrorCodes.USER_EMAIL_NOT_FOUND.message)

        val hashedPassword = userModel.password
        val verifyPassword = bCryptPasswordService.verifyPassword(request.password.toCharArray(), hashedPassword)
        return if (verifyPassword){
            val token = tokenService.generateToken(userId = userModel.id)
            TokenResponse(successful = true, message = SuccessCodes.LOGIN_SUCCESS.message, token = token)
        } else {
            TokenResponse(successful = false, message = ErrorCodes.INCORRECT_PASSWORD.message)
        }

    }

}