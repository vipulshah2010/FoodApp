package foodapp.com.data.shared.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ErrorUtils {

    fun isClientError(throwable: Throwable): Boolean {
        if (throwable is HttpException) {
            val response = throwable.response()
            return response.code() in 400..499
        }
        return false
    }

    fun isServerError(throwable: Throwable): Boolean {
        if (throwable is HttpException) {
            val response = throwable.response()
            return response.code() in 500..599
        }
        return false
    }

    fun isTimeOut(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException
    }

    fun isConnectionError(throwable: Throwable): Boolean {
        return throwable is IOException
    }
}
