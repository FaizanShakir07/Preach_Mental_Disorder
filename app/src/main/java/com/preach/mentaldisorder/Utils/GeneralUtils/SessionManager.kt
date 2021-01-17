package com.preach.mentaldisorder.Utils.GeneralUtils

import android.content.Context
import android.content.SharedPreferences
import com.preach.mentaldisorder.Models.DataModels.GeneralModels.UserModel
import com.preach.mentaldisorder.Models.Source.ServerConnection.RetrofitClientInstance
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.ADDRESS
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.BIOMETRIC_ENABLE
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.EMAIL
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.FCM_TOKEN
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.FIRST_NAME
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.IS_DELIVERY_SELECTED
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.IS_LOGGED_IN
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.IS_RE_ORDER
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.KEY_AUTH
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.LAST_NAME
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.NOTIFICATION_ENABLE
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.PHONE_NUMBER
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.PREF_NAME
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.PROFILE_PIC
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.PROFILE_STATUS
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.RESTAURANT
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.RESTAURANT_ID
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.STRIP_CUSTOMER
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.TABLE_ID
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.UNIQUE_CODE
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.USER_ID
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.USER_STATUS

class SessionManager {
    var context: Context? = null
    var pref: SharedPreferences

    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGGED_IN, false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        with(pref.edit()) {
            putBoolean(IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }


    fun isDeliverySelected(): Boolean {
        return pref.getBoolean(IS_DELIVERY_SELECTED, false)
    }

    fun setDeliverySelected(isDeliverySelected: Boolean) {
        with(pref.edit()) {
            putBoolean(IS_DELIVERY_SELECTED, isDeliverySelected)
            apply()
        }
    }
    fun isReOrder(): Boolean {
        return pref.getBoolean(IS_RE_ORDER, false)
    }

    fun setReOrder(value: Boolean) {
        with(pref.edit()) {
            putBoolean(IS_RE_ORDER, value)
            apply()
        }
    }

    fun getRestaurant(): Boolean {
        return pref.getBoolean(RESTAURANT, false)
    }

    fun setRestaurant(isRestaurant: Boolean) {
        with(pref.edit()) {
            putBoolean(RESTAURANT, isRestaurant)
            apply()
        }
    }

    fun setUserId(id: String) {
        with(pref.edit()) {
            putString(USER_ID, id)
            apply()
        }
    }

    fun getUserId(): String {
        return pref.getString(USER_ID, "")!!
    }
  fun setTableId(id: String) {
        with(pref.edit()) {
            putString(TABLE_ID, id)
            apply()
        }
    }

    fun getTableId(): String {
        return pref.getString(TABLE_ID, "")!!
    }

    fun setRestaurantId(id: String) {
        with(pref.edit()) {
            putString(RESTAURANT_ID, id)
            apply()
        }
    }

    fun getRestaurantId(): String {
        return pref.getString(RESTAURANT_ID, "")!!
    }

    fun setUniqueCode(id: String) {
        with(pref.edit()) {
            putString(UNIQUE_CODE, id)
            apply()
        }
    }

    fun getUniqueCode(): String {
        return pref.getString(UNIQUE_CODE, "")!!
    }

    fun setFirstName(name: String?) {
        with(pref.edit()) {
            putString(FIRST_NAME, name)
            apply()
        }
    }


    fun setName(name: String?) {
        with(pref.edit()) {
            putString(LAST_NAME, name)
            apply()
        }
    }

    fun getFirstName(): String {
        return pref.getString(FIRST_NAME, "")!!
    }


    fun getName(): String {
        return pref.getString(LAST_NAME, "")!!
    }


    fun setAddress(address: String?) {
        with(pref.edit()) {
            putString(ADDRESS, address)
            apply()
        }
    }

    fun getAddress(): String {
        return pref.getString(ADDRESS, "")!!
    }


    fun setLat(lat: String?) {
        with(pref.edit()) {
            putString("lat", lat)
            apply()
        }
    }

    fun getLat(): String {
        return pref.getString("lat", "")!!
    }


    fun setLng(lat: String?) {
        with(pref.edit()) {
            putString("lng", lat)
            apply()
        }
    }

    fun getLng(): String {
        return pref.getString("lng", "")!!
    }

    fun setPhoneNumber(phone_num: String?) {
        with(pref.edit()) {
            putString(PHONE_NUMBER, phone_num)
            apply()
        }
    }

    fun getPhoneNumber(): String {
        return pref.getString(PHONE_NUMBER, "")!!
    }

    fun setMobileNumber(value: String?) {
        with(pref.edit()) {
            putString(PHONE_NUMBER, value)
            apply()
        }
    }

    fun getMobileNumber(): String {
        return pref.getString(PHONE_NUMBER, "")!!
    }

    fun setPicture(imageString: String?) {
        with(pref.edit()) {
            putString(PROFILE_PIC, imageString)
            apply()
        }
    }

    fun getPicture(): String {
        return pref.getString(PROFILE_PIC, "")!!
    }


    fun setBiometric(value: Int) {
        with(pref.edit()) {
            putInt(BIOMETRIC_ENABLE, value)
            apply()
        }
    }

    fun getBiometric(): Int {
        return pref.getInt(BIOMETRIC_ENABLE, 0)
    }

    fun setNotificationEnable(value: Int) {
        with(pref.edit()) {
            putInt(NOTIFICATION_ENABLE, value)
            apply()
        }
    }

    fun getNotificationEnable(): Int {
        return pref.getInt(NOTIFICATION_ENABLE, 0)
    }

    fun setFCMToken(token: String?) {
        with(pref.edit()) {
            putString(FCM_TOKEN, token)
            apply()
        }
    }

    fun getFCMToken(): String {
        return pref.getString(FCM_TOKEN, "")!!
    }

    fun setEmail(email: String?) {
        with(pref.edit()) {
            putString(EMAIL, email)
            apply()
        }
    }

    fun getEmail(): String {
        return pref.getString(EMAIL, "")!!
    }

    fun setUserStatus(user_status: String?) {
        with(pref.edit()) {
            putString(USER_STATUS, user_status)
            apply()
        }
    }

    fun getUserStatus(): String {
        return pref.getString(USER_STATUS, "")!!
    }


    fun setStripeCustomer(stripe_customer: String?) {
        with(pref.edit()) {
            putString(STRIP_CUSTOMER, stripe_customer)
            apply()
        }
    }

    fun getStripeCustomer(): String {
        return pref.getString(STRIP_CUSTOMER, "")!!
    }


    fun setInfoStatus(status: Int?) {
        status?.let {
            with(pref.edit()) {
                putInt(PROFILE_STATUS, status)
                apply()
            }
        }

    }

    fun isProfileCompleted(): Boolean {
        return pref.getInt(PROFILE_STATUS, 1) == 1

    }


    fun setAuthToken(email: String?) {
        with(pref.edit()) {
            putString(KEY_AUTH, email)
            apply()
        }
    }

    fun getAuthToken(): String? {
        return pref.getString(KEY_AUTH, "")
    }


//    fun setPermissions(permissions: ArrayList<Permission>) {
//        var permissionsData=Gson().toJson(PermissionsData(permissions))
//        with(pref.edit()) {
//            putString(KEY_PERMISSIONS, permissionsData)
//            apply()
//        }
//        RetrofitClientInstance.getInstance(context!!)?.initRetrofit()
//    }

//    fun hasPermission(id: Int): Boolean {
//
//        var dataString=pref.getString(KEY_PERMISSIONS, "")
//        return if(dataString.isNullOrEmpty()) {
//            false
//        } else {
//            val permissionsData=Gson().fromJson(dataString,PermissionsData::class.java)
//            val reqPermission=permissionsData.permission.find { it.id==id }
//            reqPermission!=null
//        }
//    }


    fun logout() {
        setLoggedIn(false)
        setEmail("")
        setUserId("")
        setName("")
        setMobileNumber("")
        setPicture("")
        setFCMToken("")
        setUserStatus("")
        setAuthToken("")
        setUniqueCode("")
        setBiometric(0)
        setNotificationEnable(0)
        setStripeCustomer("")
    }

    fun setUser(user: UserModel) {

    }

    fun getLocale(): String {
        return "en"
    }
}