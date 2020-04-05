package bg.qponer.android.data

import bg.qponer.android.data.model.AuthenticatedUserModel

interface SessionStore {

    var jwtToken: String?

    var user: AuthenticatedUserModel?
}