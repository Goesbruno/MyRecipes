package br.com.goesbruno.myRecipes.infra.repository.user

import br.com.goesbruno.myRecipes.domain.entity.User

interface UserReadOnlyRepository {

    suspend fun findUserById(userId: String): User?
    suspend fun findUsersByIds(usersIds: List<String>): List<User>
    suspend fun checkIfUserExists(email: String): Boolean
    suspend fun checkIfUserExistsReturn(email: String): User?

}