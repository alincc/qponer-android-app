package bg.qponer.android.util

sealed class Result<out T> {

    data class Success<out T>(val payload: T) : Result<T>()

    data class Failure<out T>(val throwable: Throwable) : Result<T>()
}

fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> =
    also {
        if (this is Result.Success) {
            action(payload)
        }
    }

fun <T> Result<T>.onFailure(action: (value: Throwable) -> Unit): Result<T> =
    also {
        if (this is Result.Failure) {
            action(throwable)
        }
    }

fun <T, R> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (value: Throwable) -> R
): R =
    when (this) {
        is Result.Success -> onSuccess(payload)
        is Result.Failure -> onFailure(throwable)
    }

fun <T, R> Result<T>.map(transform: (value: T) -> R) =
    when (this) {
        is Result.Success -> Result.Success(transform(payload))
        is Result.Failure -> this
    }

fun <T, R> Result<T>.mapCatching(transform: (value: T) -> R) =
    when (this) {
        is Result.Success -> runCatching { transform(payload) }
        is Result.Failure -> this
    }

fun <T, R> Result<T>.recover(transform: (value: Throwable) -> R) =
    when (this) {
        is Result.Success -> this
        is Result.Failure -> Result.Success(transform(throwable))
    }

fun <T, R> Result<T>.recoverCatching(transform: (value: Throwable) -> R) =
    when (this) {
        is Result.Success -> this
        is Result.Failure -> runCatching { transform(throwable) }
    }

private fun <T, R> T.runCatching(block: T.() -> R): Result<R> =
    try {
        Result.Success<R>(block())
    } catch (throwable: Throwable) {
        Result.Failure<R>(throwable)
    }