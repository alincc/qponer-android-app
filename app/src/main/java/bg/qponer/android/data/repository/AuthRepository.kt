package bg.qponer.android.data.repository

import bg.qponer.android.auth.SessionStore
import bg.qponer.android.data.InvalidUserType
import bg.qponer.android.data.dto.UserType
import bg.qponer.android.data.model.AuthenticatedUserModel
import bg.qponer.android.data.service.AuthService
import bg.qponer.android.util.Result
import java.util.*

class AuthRepository(
    private val authService: AuthService,
    private val sessionStore: SessionStore
) {

    fun findCurrentUser() = sessionStore.user

    suspend fun login(username: String, password: String): Result<AuthenticatedUserModel> =
        runServiceMethod {
            authService.login(username, password).also {
                sessionStore.accessToken = it.accessToken
                sessionStore.expirationTime = Calendar.getInstance().apply {
                    add(Calendar.SECOND, it.expiresIn)
                }.timeInMillis
            }

            val userResponse = authService.me()
            if (UserType.CONTRIBUTOR == userResponse.type) {
                return@runServiceMethod AuthenticatedUserModel(
                    userResponse.id,
                    userResponse.name
                ).also { sessionStore.user = it }
            } else {
                throw InvalidUserType()
            }
        }

}