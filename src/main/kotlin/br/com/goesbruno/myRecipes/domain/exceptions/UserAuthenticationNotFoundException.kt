package br.com.goesbruno.myRecipes.domain.exceptions

import java.lang.RuntimeException

class UserAuthenticationNotFoundException(
    override val message: String?
) : RuntimeException()
