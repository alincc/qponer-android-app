package bg.qponer.android.auth

import android.app.Application
import android.content.Context
import bg.qponer.android.data.model.AuthenticatedUserModel

internal class SharedPrefsSessionStore(
    private val app: Application
) : SessionStore {

    override var jwtToken: String? = null
        get() {
            return field ?: readJwtFromPrefs()?.also { field = it }
        }
        set(value) {
            field = value
            field?.let { writeJwtToPrefs(it) }
        }

    override var user: AuthenticatedUserModel? = null
        get() {
            return field ?: readUserFromPrefs()?.also { field = it }
        }
        set(value) {
            field = value
            field?.let { writeUserToPrefs(it) }
        }

    private fun writeJwtToPrefs(jwtToken: String) {
        openPrefs()
            .edit()
            .putString("jwtToken", jwtToken)
            .apply()
    }

    private fun readJwtFromPrefs() =
        openPrefs()
            .getString("jwtToken", null)

    private fun writeUserToPrefs(user: AuthenticatedUserModel) =
        openPrefs()
            .edit()
            .putLong("userId", user.userId)
            .putString("username", user.username)
            .apply()

    private fun readUserFromPrefs(): AuthenticatedUserModel? {
        val userId = openPrefs().getLong("userId", -1)
        val username = openPrefs().getString("username", null)

        return username?.let { AuthenticatedUserModel(userId, username) }
    }

    private fun openPrefs() =
        app.getSharedPreferences("sessionStore", Context.MODE_PRIVATE)

}