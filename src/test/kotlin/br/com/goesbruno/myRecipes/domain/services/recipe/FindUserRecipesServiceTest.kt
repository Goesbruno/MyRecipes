package br.com.goesbruno.myRecipes.domain.services.recipe

import br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model.UserFactory
import br.com.goesbruno.myRecipes.application.mappers.toRecipeResponse
import br.com.goesbruno.myRecipes.domain.model.RecipesFactory
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeReadOnlyRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class FindUserRecipesServiceTest {

    private lateinit var findUserRecipesService: FindUserRecipesService
    private lateinit var recipeReadOnlyRepository: RecipeReadOnlyRepository

    private val userAna = UserFactory().create(UserFactory.FakeUser.Ana)
    private val recipesAna = RecipesFactory().create(
        userId = userAna.id,
        recipeId = UUID.randomUUID().toString(),
        recipesFactoryFake = RecipesFactory.RecipesFactoryFake.Ana,
    )

    @BeforeTest
    fun setUp() {
        recipeReadOnlyRepository = mockk()
        findUserRecipesService = FindUserRecipesService(recipeReadOnlyRepository)
    }

    @AfterTest
    fun afterDown() {
        clearAllMocks()
    }

    @Test
    fun `should return a list of user's recipes when a valid query is provided`() = runBlocking {
        val userId = userAna.id
        val recipeList = listOf(recipesAna)
        val nameOrIngredient = "Lasanha"
        coEvery { recipeReadOnlyRepository.search(eq(nameOrIngredient), eq(userId)) } returns recipeList

        val response = findUserRecipesService.search(nameOrIngredient, userId)
        val expectedResponse = recipeList.map { it.toRecipeResponse() }

        assertThat(response).isEqualTo(expectedResponse)
    }

    @Test
    fun `should return a empty list when a invalid query is provided`() = runBlocking {
        val userId = userAna.id
        val nameOrIngredient = ""
        coEvery { recipeReadOnlyRepository.search(eq(nameOrIngredient), eq(userId)) } returns emptyList()

        val response = findUserRecipesService.search(nameOrIngredient, userId)

        assertThat(response).isEmpty()
    }
}