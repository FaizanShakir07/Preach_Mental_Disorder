package com.preach.mentaldisorder.Models.DataModels.ResponseModels


import com.preach.mentaldisorder.Models.DataModels.GeneralModels.UserModel
import com.preach.mentaldisorder.Models.DataModels.UtilityModels.BaseResponse
import java.io.Serializable

data class UserResponseModel(

        val data: UserModel
): BaseResponse(), Serializable