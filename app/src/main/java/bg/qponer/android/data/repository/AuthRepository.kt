package bg.qponer.android.data.repository

import bg.qponer.android.data.InvalidUserType
import bg.qponer.android.data.SessionStore
import bg.qponer.android.data.dto.LoginRequest
import bg.qponer.android.data.dto.UserType
import bg.qponer.android.data.model.AuthenticatedUserModel
import bg.qponer.android.data.service.AuthService
import bg.qponer.android.util.Result

class AuthRepository(
    private val authService: AuthService,
    private val sessionStore: SessionStore
) {

    fun findCurrentUser() = sessionStore.user

    suspend fun login(username: String, password: String): Result<AuthenticatedUserModel> =
        runServiceMethod {
            val response = authService.login(LoginRequest(username, password))
            if (UserType.CONTRIBUTOR == response.type) {
                sessionStore.jwtToken = response.jwt
                return@runServiceMethod AuthenticatedUserModel(
                    response.customerId,
                    response.username
                )
            } else {
                throw InvalidUserType()
            }
        }

}