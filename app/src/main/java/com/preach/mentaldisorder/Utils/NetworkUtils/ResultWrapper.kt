package com.preach.mentaldisorder.Utils.NetworkUtils

import com.preach.mentaldisorder.Models.DataModels.UtilityModels.ApiErrorResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ApiErrorResponse? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}