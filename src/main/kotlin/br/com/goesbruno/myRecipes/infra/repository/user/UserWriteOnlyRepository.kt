package br.com.goesbruno.myRecipes.infra.repository.user

import br.com.goesbruno.myRecipes.domain.entity.User

interface UserWriteOnlyRepository {

    suspend fun insertUser(user: User): Boolean


}