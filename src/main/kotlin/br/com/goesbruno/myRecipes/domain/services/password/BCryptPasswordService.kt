package br.com.goesbruno.myRecipes.domain.services.password

import at.favre.lib.crypto.bcrypt.BCrypt

class BCryptPasswordService {

    fun verifyPassword(password: CharArray, hashedPassword: String): Boolean {
        return BCrypt.verifyer().verify(password, hashedPassword).verified
    }

    fun hashedPassword(cost: Int, password: String): String {
        return BCrypt.withDefaults().hashToString(cost, password.toCharArray()).toString()
    }

}