package com.preach.mentaldisorder.Models.Source.Repository


import com.preach.mentaldisorder.Models.DataModels.UtilityModels.BaseResponse
import com.preach.mentaldisorder.Models.DataSource.BaseDataSource

interface ServerDataSource {


    interface GetGeneralResponseCallback: BaseDataSource {
        fun onSuccess(baseResponse: BaseResponse)
    }

//
//    interface LoginCallback:BaseDataSource{
//        fun onLogin(login: LoginResponse)
//
//    }



}