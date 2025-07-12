package br.com.goesbruno.br.com.goesbruno.myRecipes.domain.services.user

import br.com.goesbruno.br.com.goesbruno.myRecipes.Utils.Constants
import br.com.goesbruno.br.com.goesbruno.myRecipes.application.payloads.responses.AuthRequestFactory
import br.com.goesbruno.br.com.goesbruno.myRecipes.application.payloads.responses.TokenResponseFactory
import br.com.goesbruno.br.com.goesbruno.myRecipes.domain.model.UserFactory
import br.com.goesbruno.myRecipes.domain.services.password.BCryptPasswordService
import br.com.goesbruno.myRecipes.domain.services.token.TokenService
import br.com.goesbruno.myRecipes.domain.services.user.LoginUserService
import br.com.goesbruno.myRecipes.domain.validations.AuthUserRequestValidation
import br.com.goesbruno.myRecipes.infra.repository.user.UserReadOnlyRepository
import br.com.goesbruno.myRecipes.utils.ErrorCodes
import br.com.goesbruno.myRecipes.utils.SuccessCodes
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


class LoginUserServiceTest {

    private lateinit var tokenService: TokenService
    private lateinit var bCryptPasswordService: BCryptPasswordService
    private lateinit var authUserRequestValidation: AuthUserRequestValidation
    private lateinit var userReadOnlyRepository: UserReadOnlyRepository

    private lateinit var loginUserService: LoginUserService

    private val fakeTokenResponse = TokenResponseFactory().create(
        successful = true,
        message = SuccessCodes.VALID_REGISTRATION.message,
        token = Constants.FAKE_TOKEN
    )
    private val fakeUserAna = UserFactory().create(UserFactory.FakeUser.Ana)
    private val fakeAuthUserRequest = AuthRequestFactory().create(fakeUserAna.email, fakeUserAna.password)

    @BeforeTest
    fun setUp() {
        tokenService = mockk()
        bCryptPasswordService = mockk()
        authUserRequestValidation = mockk()
        userReadOnlyRepository = mockk()

        loginUserService = LoginUserService(
            tokenService, authUserRequestValidation, bCryptPasswordService, userReadOnlyRepository
        )
    }

    @AfterTest
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun `should return a successful tokenResponse with the generated token`() = runBlocking {
        //Given
        val token = Constants.FAKE_TOKEN

        coEvery { authUserRequestValidation.validator(any()) } returns fakeTokenResponse
        coEvery { userReadOnlyRepository.checkIfUserExistsReturn(any()) } returns fakeUserAna
        every { bCryptPasswordService.verifyPassword(any(), any()) } returns true
        every { tokenService.generateToken(any()) } returns token

        //When
        val result = loginUserService.loginUser(fakeAuthUserRequest)

        //Then
        assertThat(result.token).isNotEmpty()
        assertThat(result.successful).isTrue()
    }


    @Test
    fun `should return user email not found response`() = runBlocking {
        //Given

        coEvery { authUserRequestValidation.validator(any()) } returns fakeTokenResponse
        coEvery { userReadOnlyRepository.checkIfUserExistsReturn(any()) } returns null


        //When
        val result = loginUserService.loginUser(fakeAuthUserRequest)

        //Then
        assertThat(result.token).isEmpty()
        assertThat(result.successful).isFalse()
        assertThat(result.message).isEqualTo(ErrorCodes.USER_EMAIL_NOT_FOUND.message)
    }


    @Test
    fun `should return incorrect password response`() = runBlocking {
        //Given

        coEvery { authUserRequestValidation.validator(any()) } returns fakeTokenResponse
        coEvery { userReadOnlyRepository.checkIfUserExistsReturn(any()) } returns fakeUserAna
        every { bCryptPasswordService.verifyPassword(any(), any()) } returns false

        //When
        val result = loginUserService.loginUser(fakeAuthUserRequest)

        //Then
        assertThat(result.token).isEmpty()
        assertThat(result.successful).isFalse()
        assertThat(result.message).isEqualTo(ErrorCodes.INCORRECT_PASSWORD.message)
    }
}