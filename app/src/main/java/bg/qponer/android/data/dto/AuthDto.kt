package bg.qponer.android.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val jwt: String,
    val customerId: Long,
    val username: String,
    val type: UserType
)

enum class UserType {
    BUSINESS_OWNER, CONTRIBUTOR
}