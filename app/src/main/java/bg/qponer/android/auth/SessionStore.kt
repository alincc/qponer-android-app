package bg.qponer.android.auth

import bg.qponer.android.data.model.AuthenticatedUserModel

interface SessionStore {

    var jwtToken: String?

    var user: AuthenticatedUserModel?
}