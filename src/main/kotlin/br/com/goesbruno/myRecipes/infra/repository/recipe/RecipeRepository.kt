package br.com.goesbruno.myRecipes.infra.repository.recipe

import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequest
import br.com.goesbruno.myRecipes.domain.database.DatabaseService
import br.com.goesbruno.myRecipes.domain.entity.CategoryEnum
import br.com.goesbruno.myRecipes.domain.entity.Ingredient
import br.com.goesbruno.myRecipes.domain.entity.Recipe
import br.com.goesbruno.myRecipes.domain.entity.User
import br.com.goesbruno.myRecipes.infra.repository.user.UserRepository
import br.com.goesbruno.myRecipes.utils.Constants
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import com.mongodb.MongoException
import com.mongodb.client.model.DeleteOptions
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import javax.sql.rowset.FilteredRowSet

class RecipeRepository(
    val databaseService: DatabaseService
) : RecipesWriteOnlyRepository, RecipesReadOnlyRepository {

    private val logger = LoggerFactory.getLogger(RecipeRepository::class.java)
    private val recipesCollection = databaseService.database.getCollection<Recipe>(Constants.COLLECTION_NAME_RECIPES)

    override suspend fun create(recipe: Recipe): Boolean {
        try {
            return recipesCollection.insertOne(recipe).wasAcknowledged()
        } catch (e: Exception) {
            when (e) {
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.UNKNOWN_ERROR}: ${e.message}", e)
            }
        }
        return false
    }

    override suspend fun remove(recipeId: String): Boolean {
        try {
            return recipesCollection.deleteOne(Filters.eq("_id", ObjectId(recipeId))).wasAcknowledged()
        } catch (e: Exception) {
            when (e) {
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.UNKNOWN_ERROR}: ${e.message}", e)
            }
        }
        return false
    }

    override suspend fun update(
        recipeId: String,
        addUpdateRecipeRequest: AddUpdateRecipeRequest
    ): Boolean {
        try {

            val update = recipesCollection.updateOne(
                filter = Filters.eq("_id", ObjectId(recipeId)),
                update = Updates.combine(
                    Updates.set(Recipe::name.name, addUpdateRecipeRequest.name),
                    Updates.set(Recipe::category.name, CategoryEnum.fromInt(addUpdateRecipeRequest.category)),
                    Updates.set(Recipe::preparationMode.name, addUpdateRecipeRequest.preparationMode),
                    Updates.set(Recipe::preparationTime.name, addUpdateRecipeRequest.preparationTime),
                    Updates.set(Recipe::ingredients.name, addUpdateRecipeRequest.ingredients.map { ingredientRequest ->
                        Ingredient(
                            recipeId = recipeId,
                            name = ingredientRequest.name,
                            quantity = ingredientRequest.quantity
                        )
                    }),
                )
            )
            return update.wasAcknowledged()

        } catch (e: Exception) {
            when (e) {
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.UNKNOWN_ERROR}: ${e.message}", e)
            }
        }
        return false
    }

    override suspend fun getByUser(
        userId: String,
        categoryEnum: CategoryEnum?
    ): List<Recipe> {

        try {
            val filters = mutableListOf(Filters.eq(Recipe::userId.name, userId))
            categoryEnum?.let { filters.add(Filters.eq(Recipe::category.name, it)) }
            return recipesCollection.find(Filters.and(filters)).toList()
        } catch (e: Exception) {
            when (e) {
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.UNKNOWN_ERROR}: ${e.message}", e)
            }
        }
        return emptyList()
    }

    override suspend fun getById(
        recipeId: String,
        userId: String
    ): Recipe? {
        try {

            val userRecipes = getByUser(userId, null)
            val recipe = userRecipes.singleOrNull { it.id == recipeId}
            return recipe

        } catch (e: Exception) {
            when (e) {
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.UNKNOWN_ERROR}: ${e.message}", e)
            }
        }
        return null
    }

    override suspend fun search(
        nameOrIngredients: String,
        userId: String
    ): List<Recipe> {
        try {

            val userRecipes = getByUser(userId, null).takeIf { recipes ->
                recipes.isNotEmpty()
            } ?: emptyList()

            return userRecipes.filter { recipe ->
                nameOrIngredients.isBlank() || recipe.name.contains(nameOrIngredients, true) ||
                        recipe.ingredients.any{ it.name.contains(nameOrIngredients, true)}
            }.sortedBy { it.name }

        } catch (e: Exception) {
            when (e) {
                is MongoException -> logger.error("${ErrorCodes.DATABASE_ERROR}: ${e.message}", e)
                else -> logger.error("${ErrorCodes.UNKNOWN_ERROR}: ${e.message}", e)
            }
        }
        return emptyList()
    }

    override suspend fun checkIfExists(recipeId: String): Boolean {
        val count = recipesCollection.countDocuments(Filters.eq("_id", ObjectId(recipeId)))
        return count > 0
    }
}