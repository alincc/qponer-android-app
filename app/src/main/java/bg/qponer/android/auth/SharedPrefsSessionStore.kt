package bg.qponer.android.auth

import android.app.Application
import android.content.Context
import bg.qponer.android.data.model.AuthenticatedUserModel

internal class SharedPrefsSessionStore(
    private val app: Application
) : SessionStore {

    override var accessToken: String? = null
        get() {
            return field ?: readAccessTokenFromPrefs()?.also { field = it }
        }
        set(value) {
            field = value
            field?.let { writeAccessTokenToPrefs(it) }
        }

    override var expirationTime: Long? = null
        get() = field ?: readExpirationTimeFromPrefs()?.also { field = it }
        set(value) {
            field = value
            field?.let { writeExpirationTimeToPrefs(it) } ?: removeExpirationTime()
        }
    override var refreshToken: String? = null
        get() = field ?: readRefreshTokenFromPrefs()?.also { field = it }
        set(value) {
            field = value
            field?.let { writeRefreshTokenToPrefs(it) }
        }

    override var user: AuthenticatedUserModel? = null
        get() {
            return field ?: readUserFromPrefs()?.also { field = it }
        }
        set(value) {
            field = value
            field?.let { writeUserToPrefs(it) }
        }

    private fun writeAccessTokenToPrefs(jwtToken: String) {
        openPrefs()
            .edit()
            .putString("accessToken", jwtToken)
            .apply()
    }

    private fun readAccessTokenFromPrefs() =
        openPrefs()
            .getString("accessToken", null)

    private fun writeExpirationTimeToPrefs(expirationTime: Long) =
        openPrefs()
            .edit()
            .putLong("expirationTime", expirationTime)
            .apply()

    private fun readExpirationTimeFromPrefs(): Long? {
        val expirationTime = openPrefs().getLong("expirationTime", -1L)
        return if (expirationTime == -1L) {
            null
        } else {
            expirationTime
        }
    }

    private fun removeExpirationTime() =
        openPrefs()
            .edit()
            .remove("expirationTime")
            .apply()

    private fun writeUserToPrefs(user: AuthenticatedUserModel) =
        openPrefs()
            .edit()
            .putLong("userId", user.userId)
            .putString("username", user.username)
            .apply()

    private fun writeRefreshTokenToPrefs(jwtToken: String) {
        openPrefs()
            .edit()
            .putString("refreshToken", jwtToken)
            .apply()
    }

    private fun readRefreshTokenFromPrefs() =
        openPrefs()
            .getString("refreshToken", null)

    private fun readUserFromPrefs(): AuthenticatedUserModel? {
        val userId = openPrefs().getLong("userId", -1)
        val username = openPrefs().getString("username", null)

        return username?.let { AuthenticatedUserModel(userId, username) }
    }

    private fun openPrefs() =
        app.getSharedPreferences("sessionStore", Context.MODE_PRIVATE)

}