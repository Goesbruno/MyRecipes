package br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUserRequest
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.domain.entity.User
import br.com.goesbruno.myRecipes.domain.services.password.BCryptPasswordService
import br.com.goesbruno.myRecipes.domain.validations.AddUserRequestValidation
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserWriteOnlyRepository
import br.com.goesbruno.myRecipes.utils.Constants
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes

class AddUserService(
    private val addUserRequestValidation: AddUserRequestValidation,
    private val bCryptPasswordService: BCryptPasswordService,
    private val userWriteOnlyRepository: UserWriteOnlyRepository,
    private val userReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun addUser(addUserRequest: AddUserRequest): SimpleResponse {

        val simpleResponse = addUserRequestValidation.validator(addUserRequest)
        if (!simpleResponse.successful){
            return simpleResponse
        }

        if (userReadOnlyRepository.checkIfUserExists(addUserRequest.email)){
            return SimpleResponse(successful = false, message = ErrorCodes.EMAIL_ALREADY_USED.message)
        }

        val hashedPassword = bCryptPasswordService.hashedPassword(Constants.COST_FACTOR, addUserRequest.password)

        val user = User(
            name = addUserRequest.name,
            email = addUserRequest.email,
            password = hashedPassword,
            phone = addUserRequest.phone
        )
        val result = userWriteOnlyRepository.insertUser(user)
        return if (result){
            SimpleResponse(successful = true, message = SuccessCodes.REGISTRATION_COMPLETED.message)
        } else {
            SimpleResponse(successful = false, message = ErrorCodes.REGISTRATION_ERROR.message)
        }
    }


}