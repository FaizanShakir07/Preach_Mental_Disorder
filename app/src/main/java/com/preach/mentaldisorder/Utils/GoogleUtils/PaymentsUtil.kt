package com.preach.mentaldisorder.Utils.GoogleUtils

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.Wallet.WalletOptions
import com.preach.mentaldisorder.Utils.GeneralUtils.Constants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

object PaymentsUtil {
    private val MICROS = BigDecimal(1000000.0)

    /**
     * Create a Google Pay API base request object with properties used in all requests.
     *
     * @return Google Pay API base request object.
     * @throws JSONException
     */
    @get:Throws(JSONException::class)
    private val baseRequest: JSONObject
        private get() = JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0)

    fun createPaymentsClient(activity: Activity?): PaymentsClient {
        val walletOptions =
            WalletOptions.Builder().setEnvironment(Constants.PAYMENTS_ENVIRONMENT).build()
        return Wallet.getPaymentsClient(activity!!, walletOptions)
    }

    /**
     * Gateway Integration: Identify your gateway and your app's gateway merchant identifier.
     *
     *
     * The Google Pay API response will return an encrypted payment method capable of being charged
     * by a supported gateway after payer authorization.
     *
     *
     * TODO: Check with your gateway on the parameters to pass and modify them in Constants.java.
     *
     * @return Payment data tokenization for the CARD payment method.
     * @throws JSONException
     * @see [PaymentMethodTokenizationSpecification](https://developers.google.com/pay/api/android/reference/object.PaymentMethodTokenizationSpecification)
     */
    @get:Throws(JSONException::class)
    private val gatewayTokenizationSpecification: JSONObject
        private get() = object : JSONObject() {
            init {
                put("type", "PAYMENT_GATEWAY")
                put("parameters", object : JSONObject() {
                    init {
                        put("gateway", "SmartServeTech")
                        put("gatewayMerchantId", "03005811360812174661")
                    }
                })
            }
        }

    /**
     * `DIRECT` Integration: Decrypt a response directly on your servers. This configuration has
     * additional data security requirements from Google and additional PCI DSS compliance complexity.
     *
     *
     * Please refer to the documentation for more information about `DIRECT` integration. The
     * type of integration you use depends on your payment processor.
     *
     * @return Payment data tokenization for the CARD payment method.
     * @throws JSONException
     * @see [PaymentMethodTokenizationSpecification](https://developers.google.com/pay/api/android/reference/object.PaymentMethodTokenizationSpecification)
     */
    @get:Throws(JSONException::class, RuntimeException::class)
    private val directTokenizationSpecification: JSONObject
        private get() {
            if (Constants.DIRECT_TOKENIZATION_PARAMETERS.isEmpty()
                || Constants.DIRECT_TOKENIZATION_PUBLIC_KEY.isEmpty()
                || Constants.DIRECT_TOKENIZATION_PUBLIC_KEY == null || Constants.DIRECT_TOKENIZATION_PUBLIC_KEY === "REPLACE_ME"
            ) {
                throw RuntimeException(
                    "Please edit the Constants.java file to add protocol version & public key."
                )
            }
            val tokenizationSpecification = JSONObject()
            tokenizationSpecification.put("type", "DIRECT")
            val parameters = JSONObject(Constants.DIRECT_TOKENIZATION_PARAMETERS as Map<*, *>)
            tokenizationSpecification.put("parameters", parameters)
            return tokenizationSpecification
        }

    /**
     * Card networks supported by your app and your gateway.
     *
     *
     * TODO: Confirm card networks supported by your app and gateway & update in Constants.java.
     *
     * @return Allowed card networks
     * @see [CardParameters](https://developers.google.com/pay/api/android/reference/object.CardParameters)
     */
    private val allowedCardNetworks: JSONArray
        private get() = JSONArray(Constants.SUPPORTED_NETWORKS)

    /**
     * Card authentication methods supported by your app and your gateway.
     *
     *
     * TODO: Confirm your processor supports Android device tokens on your supported card networks
     * and make updates in Constants.java.
     *
     * @return Allowed card authentication methods.
     * @see [CardParameters](https://developers.google.com/pay/api/android/reference/object.CardParameters)
     */
    private val allowedCardAuthMethods: JSONArray
        private get() = JSONArray(Constants.SUPPORTED_METHODS)// Optionally, you can add billing address/phone number associated with a CARD payment method.

    /**
     * Describe your app's support for the CARD payment method.
     *
     *
     * The provided properties are applicable to both an IsReadyToPayRequest and a
     * PaymentDataRequest.
     *
     * @return A CARD PaymentMethod object describing accepted cards.
     * @throws JSONException
     * @see [PaymentMethod](https://developers.google.com/pay/api/android/reference/object.PaymentMethod)
     */
    @get:Throws(JSONException::class)
    private val baseCardPaymentMethod: JSONObject
        private get() {
            val cardPaymentMethod = JSONObject()
            cardPaymentMethod.put("type", "CARD")
            val parameters = JSONObject()
            parameters.put("allowedAuthMethods", allowedCardAuthMethods)
            parameters.put("allowedCardNetworks", allowedCardNetworks)
            // Optionally, you can add billing address/phone number associated with a CARD payment method.
            parameters.put("billingAddressRequired", true)
            val billingAddressParameters = JSONObject()
            billingAddressParameters.put("format", "FULL")
            parameters.put("billingAddressParameters", billingAddressParameters)
            cardPaymentMethod.put("parameters", parameters)
            return cardPaymentMethod
        }

    /**
     * Describe the expected returned payment data for the CARD payment method
     *
     * @return A CARD PaymentMethod describing accepted cards and optional fields.
     * @throws JSONException
     * @see [PaymentMethod](https://developers.google.com/pay/api/android/reference/object.PaymentMethod)
     */
    @get:Throws(JSONException::class)
    private val cardPaymentMethod: JSONObject
        private get() {
            val cardPaymentMethod = baseCardPaymentMethod
            cardPaymentMethod.put("tokenizationSpecification", gatewayTokenizationSpecification)
            return cardPaymentMethod
        }

    @get:RequiresApi(api = Build.VERSION_CODES.N)
    val isReadyToPayRequest: Optional<JSONObject>
        get() = try {
            val isReadyToPayRequest = baseRequest
            isReadyToPayRequest.put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod))
            Optional.of(isReadyToPayRequest)
        } catch (e: JSONException) {
            Optional.empty()
        }



    /**
     * Provide Google Pay API with a payment amount, currency, and amount status.
     *
     * @return information about the requested payment.
     * @throws JSONException
     * @see [TransactionInfo](https://developers.google.com/pay/api/android/reference/object.TransactionInfo)
     */
    @Throws(JSONException::class)
    private fun getTransactionInfo(price: String): JSONObject {
        val transactionInfo = JSONObject()
        transactionInfo.put("totalPrice", price)
        transactionInfo.put("totalPriceStatus", "FINAL")
        transactionInfo.put("countryCode", Constants.COUNTRY_CODE)
        transactionInfo.put("currencyCode", Constants.CURRENCY_CODE)
        return transactionInfo
    }

    /**
     * Information about the merchant requesting payment information
     *
     * @return Information about the merchant.
     * @throws JSONException
     * @see [MerchantInfo](https://developers.google.com/pay/api/android/reference/object.MerchantInfo)
     */
    @get:Throws(JSONException::class)
    private val merchantInfo: JSONObject
        private get() = JSONObject().put("merchantName", "Smart serve Tech")

    /**
     * An object describing information requested in a Google Pay payment sheet
     *
     * @return Payment data expected by your app.
     * @see [PaymentDataRequest](https://developers.google.com/pay/api/android/reference/object.PaymentDataRequest)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun getPaymentDataRequest(price: String): Optional<JSONObject> {
        return try {
            val paymentDataRequest = baseRequest
            paymentDataRequest.put(
                "allowedPaymentMethods", JSONArray().put(cardPaymentMethod)
            )
            paymentDataRequest.put("transactionInfo", getTransactionInfo(price))
            paymentDataRequest.put("merchantInfo", merchantInfo)

            /* An optional shipping address requirement is a top-level property of the PaymentDataRequest
      JSON object. */paymentDataRequest.put("shippingAddressRequired", false)
            val shippingAddressParameters = JSONObject()
            shippingAddressParameters.put("phoneNumberRequired", false)
            val allowedCountryCodes = JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES)
            shippingAddressParameters.put("allowedCountryCodes", allowedCountryCodes)
            paymentDataRequest.put("shippingAddressParameters", shippingAddressParameters)
            Optional.of(paymentDataRequest)
        } catch (e: JSONException) {
            Optional.empty()
        }
    }

    /**
     * Converts micros to a string format accepted by [PaymentsUtil.getPaymentDataRequest].
     *
     * @param micros value of the price.
     */
    fun microsToString(micros: Long): String {
        return BigDecimal(micros).divide(MICROS).setScale(2, RoundingMode.HALF_EVEN).toString()
    }
}