package com.booklet.bookletandroid.domain.model

data class Result<out T>(val status: Status,
                         val data: T?,
                         val message: String?,
                         val errorType: ErrorType?) {
    companion object {
        fun <T> success(data: T): Result<T> =
                Result(status = Status.SUCCESS,
                        data = data,
                        message = null,
                        errorType = null)

        fun <T> error(data: T?, errorType: ErrorType, message: String): Result<T> =
                Result(status = Status.ERROR,
                        data = data,
                        message = message,
                        errorType = errorType)

        fun <T> loading(data: T?): Result<T> =
                Result(status = Status.LOADING,
                        data = data,
                        message = null,
                        errorType = null)
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    enum class ErrorType {
        MISSING_NETWORK_ERROR,
        SERVER_ERROR,
        UNKNOWN_ERROR,
    }
}