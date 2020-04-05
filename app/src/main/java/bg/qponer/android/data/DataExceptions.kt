package bg.qponer.android.data

class NoConnectionException() : Exception()

class ServerUnreachableException() : Exception()

class HttpClientException(
    val code: Int,
    val errorMessage: String
) : Exception("$code: $errorMessage")

class HttpServerException(
    val code: Int,
    val errorMessage: String
) : Exception("$code: $errorMessage")

class InvalidUserType() : Exception()