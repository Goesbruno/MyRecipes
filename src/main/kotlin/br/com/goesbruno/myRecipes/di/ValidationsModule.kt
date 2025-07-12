package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.domain.validations.AddUserRequestValidation
import br.com.goesbruno.myRecipes.domain.validations.AddUserRequestValidationImpl
import br.com.goesbruno.myRecipes.domain.validations.AuthUserRequestValidation
import br.com.goesbruno.myRecipes.domain.validations.AuthUserRequestValidationImpl
import org.koin.dsl.module

object ValidationsModule {

    val module = module {
        single<AddUserRequestValidation> { AddUserRequestValidationImpl() }
        single< AuthUserRequestValidation> { AuthUserRequestValidationImpl() }

    }
}