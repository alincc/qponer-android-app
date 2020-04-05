package bg.qponer.android.data.service

import bg.qponer.android.data.dto.LoginRequest
import bg.qponer.android.data.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}