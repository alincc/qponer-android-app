package bg.qponer.android.auth

import bg.qponer.android.data.model.AuthenticatedUserModel

interface SessionStore {

    var accessToken: String?

    var expirationTime: Long?

    var refreshToken: String?

    var user: AuthenticatedUserModel?
}