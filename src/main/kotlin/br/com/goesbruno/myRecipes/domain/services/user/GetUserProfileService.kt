package br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.myRecipes.application.mappers.toUserReponse
import br.com.goesbruno.myRecipes.application.payloads.responses.UserResponse
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository

class GetUserProfileService (
    private val userReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun getUserProfileById(userId: String): UserResponse? {
        val userModel = userReadOnlyRepository.findUserById(userId = userId)
        return userModel?.toUserReponse()
    }

}