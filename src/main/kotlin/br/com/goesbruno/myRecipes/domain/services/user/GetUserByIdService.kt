package br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.myRecipes.domain.entity.User
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import br.com.goesbruno.myRecipes.infra.repository.user.UserRepository

class GetUserByIdService(
    private val userReadOnlyRepository: UserReadOnlyRepository
) {

    suspend fun getUserById(userId: String): User? {
        return userReadOnlyRepository.findUserById(userId)
    }

}