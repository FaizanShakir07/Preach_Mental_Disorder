package com.preach.mentaldisorder.Models.DataSource

import com.preach.mentaldisorder.Models.DataModels.UtilityModels.ApiErrorResponse


interface BaseDataSource {
    fun onPayloadError(error: ApiErrorResponse)
}
