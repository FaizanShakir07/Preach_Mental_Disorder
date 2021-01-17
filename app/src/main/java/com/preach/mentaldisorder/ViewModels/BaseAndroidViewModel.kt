package com.preach.mentaldisorder.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.preach.mentaldisorder.Models.DataModels.UtilityModels.BaseResponse
import com.preach.mentaldisorder.Models.DataModels.UtilityModels.ErrorResponse
import com.preach.mentaldisorder.Utils.GeneralUtils.OneShotEvent
import com.preach.mentaldisorder.Utils.NetworkUtils.ResultWrapper


open class BaseAndroidViewModel() : ViewModel() {


    val snackbarMessage = MutableLiveData<OneShotEvent<String>>()
    val errorResponse = MutableLiveData<OneShotEvent<ErrorResponse>>()
    val progressBar = MutableLiveData<OneShotEvent<Boolean>>()
    val loader = MutableLiveData<OneShotEvent<Boolean>>()




    protected fun showSnackbarMessage(message: String) {
        snackbarMessage.value = OneShotEvent(message)
    }

    protected fun showNetworkError() {
        snackbarMessage.value = OneShotEvent("Internet connection problem")
    }

    protected fun handleErrorType(error:ResultWrapper<Any>) {
        when (error) {
            is ResultWrapper.NetworkError ->
                showNetworkError()
            is ResultWrapper.GenericError ->
                showSnackbarMessage("" + error.error?.message)
        }
    }

    protected fun isSuccess(result:BaseResponse):Boolean {
        if(result.status==200)
        {
            return true
        }
        else
        {
            showSnackbarMessage(result.message)
            return false
        }
    }



    protected fun showProgressBar(show: Boolean) {
        progressBar.value = OneShotEvent(show)
    }




}