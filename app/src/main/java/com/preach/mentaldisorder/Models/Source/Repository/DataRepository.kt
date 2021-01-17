package com.preach.mentaldisorder.Models.Source.Repository

import android.content.Context
import com.preach.mentaldisorder.Models.Source.ServerConnection.RetrofitClientInstance
import com.preach.mentaldisorder.Utils.GeneralUtils.SessionManager


class DataRepository(
    ctx: Context,
    retroInstance: RetrofitClientInstance,
    sessionManager: SessionManager
) : BaseRepository(ctx, retroInstance, sessionManager) {


//
//    val allItems: LiveData<List<MenuItemModel>> = cartDao.getAllItems()
//    val getAllAddress: LiveData<List<AddressModel>> = cartDaoAddress.getAllAddress()
//    var menuItem: LiveData<MenuItemModel>? = null
//    var address: LiveData<AddressModel>? = null

    // ====================================== PROFILE APIS =================================
//
//    suspend fun userLogin(
//        loginRequestModel: LoginRequestModel
//    ): ResultWrapper<UserResponseModel> {
//        return safeApiCall(dispatcher) {
//            getApiService().loginUser(loginRequestModel)
//        }
//    }
//

}