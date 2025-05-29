package br.com.goesbruno.myRecipes.application.mappers

import br.com.goesbruno.myRecipes.application.payloads.responses.UserResponse
import br.com.goesbruno.myRecipes.domain.entity.User

fun User?.toUserReponse() = UserResponse(
    id = this?.id.orEmpty(),
    name = this?.name.orEmpty(),
    email = this?.email.orEmpty(),
    phone = this?.phone.orEmpty(),
    createdAt = this?.createdAt.orEmpty()
)