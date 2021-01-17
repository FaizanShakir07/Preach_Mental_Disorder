package com.preach.mentaldisorder.Models.Source.Repository

import android.content.Context
import com.preach.mentaldisorder.Models.Source.ServerConnection.ApiService
import com.preach.mentaldisorder.Models.Source.ServerConnection.RetrofitClientInstance

import com.google.gson.Gson
import com.preach.mentaldisorder.Utils.GeneralUtils.SessionManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


open class BaseRepository(ctx: Context,var retroInstance:RetrofitClientInstance,  var sessionManager: SessionManager) {

    var context: Context
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
    var userId:String =""

    var gson = Gson()

    init {
        context = ctx
        userId=sessionManager!!.getUserId()

    }

    fun getApiService(): ApiService {
        if(userId.isNullOrEmpty())
        {
            userId=sessionManager!!.getUserId()
        }
        return retroInstance.getService()
    }













}