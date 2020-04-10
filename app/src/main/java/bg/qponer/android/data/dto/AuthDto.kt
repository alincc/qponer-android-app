package bg.qponer.android.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type") val tokenType: String,
    @Json(name = "expires_in") val expiresIn: Int,
    @Json(name = "refresh_token") val refreshToken: String?,
    val scope: String?
)

@JsonClass(generateAdapter = true)
data class AuthUserResponse(
    val id: Long,
    val name: String,
    val type: UserType
)

enum class UserType {
    BUSINESS_OWNER, CONTRIBUTOR
}