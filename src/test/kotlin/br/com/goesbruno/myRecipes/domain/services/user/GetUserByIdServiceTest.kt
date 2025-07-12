package br.com.goesbruno.br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model.UserFactory
import br.com.goesbruno.myRecipes.domain.services.user.GetUserByIdService
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetUserByIdServiceTest {

    private lateinit var userReadOnlyRepository: UserReadOnlyRepository
    private lateinit var getUserByIdService: GetUserByIdService
    private val  userAna = UserFactory().create(UserFactory.FakeUser.Ana)

    @BeforeTest
    fun setUp(){
        userReadOnlyRepository = mockk()

        getUserByIdService = GetUserByIdService(userReadOnlyRepository)
    }

    @AfterTest
    fun tearDown(){
        clearAllMocks()
    }

    @Test
    fun `should return a User when a valid Id is provided`() = runBlocking {
        coEvery { userReadOnlyRepository.findUserById(any()) } returns userAna

        val result = getUserByIdService.getUserById(userAna.id)

        assertThat(result).isEqualTo(userAna)
    }

    @Test
    fun `should return null when a non existing Id is provided`() = runBlocking {
        coEvery { userReadOnlyRepository.findUserById(any()) } returns null

        val result = getUserByIdService.getUserById(userAna.id)

        assertThat(result).isNull()
    }

}