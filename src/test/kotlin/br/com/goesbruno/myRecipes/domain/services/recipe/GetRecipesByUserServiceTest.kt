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
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetRecipesByUserServiceTest {

    private lateinit var getRecipesByUserService: GetRecipesByUserService
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
        getRecipesByUserService = GetRecipesByUserService(recipeReadOnlyRepository)
    }

    @AfterTest
    fun afterDown() {
        clearAllMocks()
    }

    @Test
    fun `should return a list of recipeResponse from the user`() = runBlocking {
        val userId = userAna.id
        val recipesAna = listOf(recipesAna)
        coEvery { recipeReadOnlyRepository.getByUser(userId, null) } returns recipesAna

        val result = getRecipesByUserService.getAll(userId, null)
        val expectedRecipesResponse = recipesAna.map { it.toRecipeResponse() }

        assertThat(result).isEqualTo(expectedRecipesResponse)
    }

}