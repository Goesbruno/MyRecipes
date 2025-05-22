package br.com.goesbruno.myRecipes.di

import br.com.goesbruno.myRecipes.domain.validations.AddUserRequestValidation
import br.com.goesbruno.myRecipes.domain.validations.AddUserRequestValidationImpl
import org.koin.dsl.module

object ValidationsModule {

    val module = module {
        single<AddUserRequestValidation> { AddUserRequestValidationImpl() }

    }
}