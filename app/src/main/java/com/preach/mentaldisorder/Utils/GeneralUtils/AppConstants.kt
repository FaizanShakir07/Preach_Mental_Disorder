package com.preach.mentaldisorder.Utils.GeneralUtils

import com.theartofdev.edmodo.cropper.CropImage

class AppConstants {

    companion object {


        // REQUEST CODES
        const val PHONE_CODE = 200
        const val ADDRESS_CODE = 111
        const val PAYMENT_CODE = 1919
        const val LOCATION_CODE = 101
        const val GPS_CODE = 100

        val REQUEST_CODES= intArrayOf(
            PHONE_CODE,
            ADDRESS_CODE,
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE,
            PAYMENT_CODE,
            LOCATION_CODE,
            GPS_CODE
        )

        
        // WEB VIEW URLS

        const val RETURN_URL = "https://smartservetech.com/returns/"
        const val TERMS_AND_CONDITION_URL = "https://smartservetech.com/terms-and-conditions/"
        const val PRIVACY_POLICY_URL = "https://smartservetech.com/privacy-policy/"
        const val STRIPE_PRODUCTION_KEY = "pk_live_HaHAZ4irTrIFlnzcV8b02OkS00zfjEq7zj"
        const val STRIPE_TEST_KEY = "pk_test_KjgLslONR5EYBrV3nW5uxhYc00H4NgLpkY"
        const val HELP_FEEDBACK_URL = "https://smartservetech.com/support/"



        // APIS
        const val SIGNIN_API = "user/auth/authenticate"
        const val SIGNUP_API = "consumer/signup"
        const val LOGOUT_API="user/auth/logout"
        const val CHANGE_PASSWORD="user/password/change"
        const val FORGOT_PASSWORD="user/password/forgot"
        const val VERIFY_CODE="verify_code"
        const val UPDATE_PASSWORD="update_password"
        const val GET_PROFILE="user/me"
        const val UPDATE_PROFILE="user/me"
        const val UPDATE_FCM="update_fcm"
        const val RESTAURANT_BY_CODE="consumer/restaurant/code"
        const val ADD_RESERVATION="consumer/reservation"
        const val RESTAURANT_BY_LOCATION="consumer/restaurant"
        const val CREATE_STRIPE_CUSTOMER="consumer/stripe/customer"
        const val CREATE_STRIPE_CART="consumer/stripe/customer/card"
        const val GET_STRIPE_CART="consumer/stripe/customer/card"
        const val CREATE_PAYMENT="consumer/stripe/payment"
        const val GET_REWARDS="consumer/rewards"
        const val REMOVE_FAV="consumer/restaurant/{rest_id}/favourite"
        const val ADD_FAV="consumer/restaurant/{rest_id}/favourite"
        const val GET_RESTAURANT_REVIEWS="consumer/reviews/restaurant/{rest_id}"
        const val GET_RESTAURANT_DETAIL="consumer/restaurant/{rest_id}"
        const val GET_RESTAURANT_LIST="consumer/restaurant"
        const val GET_FAV_RESTAURANT_LIST="consumer/restaurant/favourite"
        const val GET_LOYALTY_POINTS="consumer/points"
        const val GET_ORDERS="consumer/order"
        const val SEND_REFERRAL="user/referrals/restaurant/invite"
        const val SEND_MESSAGE="consumer/notification/message/send"
        const val FEED_BACK="consumer/reviews/restaurant/{rest_id}"
        const val GET_LATEST_ORDER_INFO="consumer/order/get/latest/order/info/{order_Id}"
        const val CALL_WAITER="consumer/notification/smartcall/send"
        const val CHECK_PROMO_CODE="consumer/rewards/promocode/redeem/{rest_id}"
        const val REDEEME_REWARD="consumer/rewards/{rest_id}"
        const val REDEEM_REWARD="consumer/points"
        const val PLACE_ORDER="consumer/order/restaurant/{rest_id}"
        const val RE_ORDER="consumer/order/reorder"
        const val GET_PAYMENT_METHOD="consumer/platform/payment/methods"


        // Preference name

        const val PREF_NAME = "GarageAppPrefs"
        const val IS_LOGGED_IN = "isLoggedIn"
        const val IS_DELIVERY_SELECTED = "IsDeliverySelected"
        const val IS_RE_ORDER = "is_re_order"
        const val RESTAURANT = "restaurant"
        const val KEY_AUTH = "auth"
        const val PROFILE_PIC = "image"
        const val RES_ID = "uuid"
        const val BIOMETRIC_ENABLE = "biometric_enable"
        const val NOTIFICATION_ENABLE = "notification_enabled"
        const val ADDRESS = "address"
        const val USER_ID = "user_id"
        const val TABLE_ID = "table_id"
        const val RESTAURANT_ID = "restaurant_id"
        const val UNIQUE_CODE = "unique_code"
        const val EMAIL = "email"
        const val USER_STATUS = "user_status"
        const val STRIP_CUSTOMER = "stripe_customer"
        const val FCM_TOKEN = "fcm_token"
        const val FRAGMENT_NAME  = "fragment_name"
        const val WEB_URL  = "web_url"
        const val PHONE_NUMBER = "phone_num"
        const val IMAGE = "image"
        const val PASSWORD = "password"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"

        const val KEY_PERMISSIONS = "key_permission"
        const val NOTIFY_STATUS = "notify_status"
        const val PROFILE_STATUS = "PROFILE_STATUS"
        const val ANDROID = "android"

        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val PROMO_CODE = "promocode"
        const val SCAN_QR_CODE = "SCAN_QR_CODE"
        const val ITEM_LIST = "ITEM_LIST"


        //EXTRAS

        const val ERROR = "error"
        const val PAYMENT_RESULT = "PAYMENT_RESULT"
        const val PAYMENT_MODEL = "payment_model"
        const val PAYMENT_AMOUNT = "PAYMENT_AMOUNT"
        const val MOBILE = "mobile"
        const val FRATGMENT_TAG = "TAG"
        const val VERIFIED = "Verified"
        const val CURRENT_ADDRESS = "Current Address"
        const val CHARGE_TYPE = "Percentage"
        const val SMILE = "smile"
        const val SAD = "sad"
        const val NORMAL = "normal"




        // adapter types

        const val CATEGORY ="Category"
        const val SORT_FILTER ="sort"
        const val PRICE_FILTER ="price"
        const val DIETARY_FILTER ="dietary"
        const val GROUP ="Group"
        const val REWARD_REVIEW_ADAPTER ="RewardReviewsList"
        const val OPENING_TIMING_ADAPTER ="OpenTiming"

        // order types uuid
        const val COLLECTION_TYPE = "Collection"
        const val COLLECTION_UUID= "048aaf25-0c68-4977-a521-04fd090c8357"
        const val QR_CODE_TYPE = "QRCode"
        const val QR_CODE_UUID = "88976b38-1f17-4758-896a-42c9aeabebf3"
        const val DELIVERY_TYPE = "Delivery"
        const val DELIVERY_UUID = "eb0d5d43-2212-405a-84c4-a128ba575fb2"
        const val PAYMENT_TYPE = "Card"
        const val PAYMENT_TYPE_UUID = "5c5f91a8-a8b9-46bc-a116-3856bc5a4521"
        const val CARD_UUID = "cc1b82b0-0e4a-44d1-843d-66d8ad09d5d0"

    }
}