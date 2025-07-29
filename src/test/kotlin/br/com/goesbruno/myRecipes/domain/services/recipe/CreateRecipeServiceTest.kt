package br.com.goesbruno.myRecipes.domain.services.recipe

import br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model.UserFactory
import br.com.goesbruno.myRecipes.application.payloads.requests.AddUpdateRecipeRequestFactory
import br.com.goesbruno.myRecipes.application.payloads.responses.SimpleResponse
import br.com.goesbruno.myRecipes.domain.validations.RecipeRequestValidation
import br.com.goesbruno.myRecipes.infra.repository.recipe.RecipeWriteOnlyRepository
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreateRecipeServiceTest {


    private lateinit var createRecipeService: CreateRecipeService
    private lateinit var addRecipeRequestValidation: RecipeRequestValidation
    private lateinit var recipeWriteOnlyRepository: RecipeWriteOnlyRepository

    private val userAna = UserFactory().create(UserFactory.FakeUser.Ana)
    private val addUpdateRecipeRequest = AddUpdateRecipeRequestFactory().create()
    private val simpleResponse = SimpleResponse(
        successful = true, message = SuccessCodes.VALID_REGISTRATION.message
    )

    @BeforeTest
    fun setUp() {

        addRecipeRequestValidation = mockk()
        recipeWriteOnlyRepository = mockk()

        createRecipeService = CreateRecipeService(
            recipeRequestValidation = addRecipeRequestValidation,
            recipeWriteOnlyRepository = recipeWriteOnlyRepository
        )
    }

    @AfterTest
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun `should return success when adding a valid recipe`() = runBlocking {

        val userAnaId = userAna.id

        coEvery { addRecipeRequestValidation.validator(any(), any()) } returns simpleResponse
        coEvery { recipeWriteOnlyRepository.create(any()) } returns true
        val result = createRecipeService.create(addUpdateRecipeRequest, userAnaId)

        assertThat(result.successful).isTrue()
        assertThat(result.message).isEqualTo(SuccessCodes.RECIPE_REGISTRATION_SUCCESS.message)


    }

    @Test
    fun `should return failure when recipes addition fails`() = runBlocking {

        val userAnaId = userAna.id

        coEvery { addRecipeRequestValidation.validator(any(), any()) } returns simpleResponse
        coEvery { recipeWriteOnlyRepository.create(any()) } returns false
        val result = createRecipeService.create(addUpdateRecipeRequest, userAnaId)

        assertThat(result.successful).isFalse()
        assertThat(result.message).isEqualTo(ErrorCodes.RECIPE_REGISTRATION_ERROR.message)


    }

}