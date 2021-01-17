package com.preach.mentaldisorder.Utils.GeneralUtils

import com.preach.mentaldisorder.Models.DataModels.UtilityModels.ApiErrorResponse

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


object ErrorUtils {
    fun parseError(apiError: String,t: Throwable): ApiErrorResponse {
        try {
            val json = JSONObject(apiError)
            var error = ApiErrorResponse(
                json.optInt("code", 0),
                json.optString("message", ""))

            if(json.has("errors"))
            {
                error= getProperErrorDataMessage(json)
            }
            else if(error.message.contains("Field"))
            {
                error= getProperErrorMessage(json)
            }
            return error
        }catch (ex: Exception){
            ex.printStackTrace()
            return parseError(t)
        }
    }



    fun parseError(t: Throwable): ApiErrorResponse {
        try {
            return ApiErrorResponse(
                0,
                t.message!!
            )
        }catch (ex: Exception){
            ex.printStackTrace()
            return ApiErrorResponse(
                0,
                ""
            )
        }
    }

    private fun getProperErrorMessage(json:JSONObject):ApiErrorResponse
    {
        var jsonObject=json.getJSONObject("data")
        var code=json.optInt("code", 0)
        var message=""
        val iter = jsonObject.keys()
        if(iter.hasNext()) {
            val key = iter.next()
            try {
                var value = jsonObject[key] as JSONArray
                message=value.getString(0)
            } catch (e: JSONException) {
                // Something went wrong!
            }
        }
        return ApiErrorResponse(code,message)
    }
    private fun getProperErrorDataMessage(json:JSONObject):ApiErrorResponse
    {
        var jsonObject=json.getJSONObject("errors")
        var code=json.optInt("code", 0)
        var message=""
        val iter = jsonObject.keys()
        if(iter.hasNext()) {
            val key = iter.next()
            try {
                var value = jsonObject[key] as JSONArray
                message=value.getString(0)
            } catch (e: JSONException) {
                // Something went wrong!
            }
        }
        return ApiErrorResponse(code,message)
    }
}