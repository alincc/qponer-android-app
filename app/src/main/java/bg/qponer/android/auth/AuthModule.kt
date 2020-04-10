package bg.qponer.android.auth

import android.app.Application

class AuthModule(
    app: Application
) {

    val session: SessionStore = SharedPrefsSessionStore(app)

}