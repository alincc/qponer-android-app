package bg.qponer.android.data.repository

import bg.qponer.android.data.HttpClientException
import bg.qponer.android.data.HttpServerException
import bg.qponer.android.data.NoConnectionException
import bg.qponer.android.data.ServerUnreachableException
import bg.qponer.android.util.Result
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal suspend inline fun <T, R> T.runServiceMethod(block: () -> R): Result<R> =
    try {
        Result.Success(block())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> Result.Failure(NoConnectionException())
            is UnknownHostException -> Result.Failure(ServerUnreachableException())
            is HttpException -> when (throwable.code()) {
                in 400..499 -> Result.Failure(
                    HttpClientException(
                        throwable.code(),
                        throwable.message()
                    )
                )
                in 500..599 -> Result.Failure(
                    HttpServerException(
                        throwable.code(),
                        throwable.message()
                    )
                )
                else -> Result.Failure(throwable)
            }
            else -> Result.Failure(throwable)
        }
    }