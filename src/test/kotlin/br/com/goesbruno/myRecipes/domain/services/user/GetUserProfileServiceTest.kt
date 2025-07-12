package br.com.goesbruno.br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model.UserFactory
import br.com.goesbruno.myRecipes.application.mappers.toUserReponse
import br.com.goesbruno.myRecipes.domain.services.user.GetUserByIdService
import br.com.goesbruno.myRecipes.domain.services.user.GetUserProfileService
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetUserProfileServiceTest {
    private lateinit var userReadOnlyRepository: UserReadOnlyRepository
    private lateinit var getUserProfileService: GetUserProfileService
    private val  userAna = UserFactory().create(UserFactory.FakeUser.Ana)

    @BeforeTest
    fun setUp(){
        userReadOnlyRepository = mockk()

        getUserProfileService = GetUserProfileService(userReadOnlyRepository)
    }

    @AfterTest
    fun tearDown(){
        clearAllMocks()
    }

    @Test
    fun `should return null when a non existing Id is provided`() = runBlocking {
        val userId = "12345"
        coEvery { userReadOnlyRepository.findUserById(any()) } returns null

        val result = getUserProfileService.getUserProfileById(userId)

        assertThat(result).isNull()

    }


    @Test
    fun `should return a user profile when a valid Id is provided`() = runBlocking {
        coEvery { userReadOnlyRepository.findUserById(any()) } returns userAna

        val result = getUserProfileService.getUserProfileById(userAna.id)
        val expectedResult = userAna.toUserReponse()

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(expectedResult)

    }
}