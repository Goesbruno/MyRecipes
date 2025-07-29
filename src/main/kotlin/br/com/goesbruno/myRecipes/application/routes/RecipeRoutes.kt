package br.com.goesbruno.myRecipes.application.routes

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequest
import br.com.goesbruno.myRecipes.domain.extensions.getUserAuthentication
import br.com.goesbruno.myRecipes.domain.services.recipe.CreateRecipeService
import br.com.goesbruno.myRecipes.domain.services.recipe.GetUserRecipesService
import br.com.goesbruno.myRecipes.utils.Constants
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.recipeRoutes(
    createRecipeService: CreateRecipeService,
    getUserRecipesService: GetUserRecipesService
) {

    route(Constants.RECIPE_ROUTE) {
        authenticate {
            create(createRecipeService)
            getAll(getUserRecipesService)
        }
    }

}

fun Route.create(createRecipeService: CreateRecipeService) {
    post {
        val userId = call.getUserAuthentication()
        try {
            val request = call.receiveNullable<AddUpdateRecipeRequest>()
            if (request != null) {
                val simpleResponse = createRecipeService.create(request, userId)
                if (simpleResponse.successful) {
                    call.respond(HttpStatusCode.Created, simpleResponse)
                } else {

                    call.respond(HttpStatusCode.BadRequest, simpleResponse)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        } catch (e: ServerResponseException) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.getAll(getUserRecipesService: GetUserRecipesService) {
    get {
        val userId = call.getUserAuthentication()
        try {
            val category = call.parameters[Constants.PARAM_CATEGORY]?.toIntOrNull()
            val recipes = getUserRecipesService.getAll(userId, category)
            call.respond(HttpStatusCode.OK, recipes)
        } catch (e: ServerResponseException) {
            application.log.error(e.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
