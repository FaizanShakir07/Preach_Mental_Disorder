package com.preach.mentaldisorder.Utils.NetworkUtils

import android.content.Context
import android.net.ConnectivityManager
import com.preach.mentaldisorder.Models.DataModels.UtilityModels.ApiErrorResponse
import com.preach.mentaldisorder.Utils.GeneralUtils.ErrorUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(0, ApiErrorResponse(0,""+throwable.message))
                }
            }
        }
    }
}


private fun convertErrorBody(throwable: HttpException): ApiErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.let {
            return ErrorUtils.parseError(it.string(),throwable)
        }
    } catch (exception: Exception) {
        ApiErrorResponse(throwable.code(),throwable.message())
    }
}


fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    //should check null because in airplane mode it will be null
    return netInfo != null && netInfo.isConnected
}