package bg.qponer.android.data.service

import bg.qponer.android.data.dto.AuthUserResponse
import bg.qponer.android.data.dto.LoginResponse
import bg.qponer.android.network.RequestAuthorizationType
import retrofit2.http.*

interface AuthService {

    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String = "read",
        @Field("grant_type") grantType: String = "password",
        @Tag authorizationType: RequestAuthorizationType = RequestAuthorizationType.BASIC
    ): LoginResponse



    @GET("api/me")
    suspend fun me(): AuthUserResponse
}