package br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model

import br.com.goesbruno.myRecipes.domain.entity.User

class UserFactory {


    fun create(user: FakeUser) = when (user) {
        FakeUser.Ana -> {
            User (
                name = "Ana",
                email = "ana@gmail.com",
                password = "12345678",
                phone = "11 9 4002-8922"
            )
        }
        FakeUser.Alex -> {
            User (
                name = "Alex",
                email = "alex@gmail.com",
                password = "12345678",
                phone = "11 9 4002-8992"
            )
        }
    }


    sealed class FakeUser {
        data object Ana: FakeUser()
        data object Alex: FakeUser()
    }
}