package br.com.goesbruno.myRecipes.domain.validations

import br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model.UserFactory
import br.com.goesbruno.myRecipes.application.payloads.requests.RecipeRequestValidationFactory
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class RecipeRequestValidationImplTest {

    private lateinit var addValidationRecipeRequest: RecipeRequestValidation

    private val request = RecipeRequestValidationFactory().create()
    private val userAna = UserFactory().create(UserFactory.FakeUser.Ana)

    @BeforeTest
    fun setUp() {
        addValidationRecipeRequest = RecipeRequestValidationImpl()
    }

    @Test
    fun `should validate recipe successfully when input is valid`() = runBlocking {

        // GIVEN
        val userAnnaId = userAna.id

        // WHEN
        val response = addValidationRecipeRequest.validator(request, userAnnaId)

        // THEN
        assertThat(response.successful).isTrue()
        assertThat(response.message).isEqualTo(SuccessCodes.VALID_REGISTRATION.message)
    }

    @Test
    fun `should return error when user ID is null`() = runBlocking {

        // WHEN
        val response = addValidationRecipeRequest.validator(request, null)

        // THEN
        assertThat(response.successful).isFalse()
        assertThat(response.message).isEqualTo(ErrorCodes.USER_ID_NOT_FOUND.message)
    }

    @Test
    fun `should return error when recipe title is empty`() = runBlocking {

        // GIVEN
        val userAnnaId = userAna.id
        val recipesRequest = request.copy(name = "")

        // WHEN
        val response = addValidationRecipeRequest.validator(recipesRequest, userAnnaId)

        // THEN
        assertThat(response.successful).isFalse()
        assertThat(response.message).isEqualTo(ErrorCodes.TITLE_REQUIRED.message)
    }

    @Test
    fun `should return error when recipe category is negative`() = runBlocking {

        // GIVEN
        val userAnnaId = userAna.id
        val recipesRequest = request.copy(category = -1)

        // WHEN
        val response = addValidationRecipeRequest.validator(recipesRequest, userAnnaId)

        // THEN
        assertThat(response.successful).isFalse()
        assertThat(response.message).isEqualTo(ErrorCodes.CATEGORY_REQUIRED.message)
    }

    @Test
    fun `should return error when preparation time is empty`() = runBlocking {

        // GIVEN
        val userAnnaId = userAna.id
        val recipesRequest = request.copy(preparationTime = "")

        // WHEN
        val response = addValidationRecipeRequest.validator(recipesRequest, userAnnaId)

        // THEN
        assertThat(response.successful).isFalse()
        assertThat(response.message).isEqualTo(ErrorCodes.PREPARATION_TIME_REQUIRED.message)
    }

    @Test
    fun `should return error when preparation mode is empty`() = runBlocking {

        // GIVEN
        val userAnnaId = userAna.id
        val recipesRequest = request.copy(preparationMode = "")

        // WHEN
        val response = addValidationRecipeRequest.validator(recipesRequest, userAnnaId)

        // THEN
        assertThat(response.successful).isFalse()
        assertThat(response.message).isEqualTo(ErrorCodes.PREPARATION_MODE_REQUIRED.message)
    }

    @Test
    fun `should return error when ingredients list is empty`() = runBlocking {

        // GIVEN
        val userAnnaId = userAna.id
        val recipesRequest = request.copy(ingredients = emptyList())

        // WHEN
        val response = addValidationRecipeRequest.validator(recipesRequest, userAnnaId)

        // THEN
        assertThat(response.successful).isFalse()
        assertThat(response.message).isEqualTo(ErrorCodes.INGREDIENTS_REQUIRED.message)
    }

}