package com.preach.mentaldisorder.Utils.Application


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Patterns
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.preach.mentaldisorder.R
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants
import com.preach.mentaldisorder.Utils.GeneralUtils.DialogUtils
import com.preach.mentaldisorder.Views.adapters.BaseAdapter
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.nlopez.smartlocation.SmartLocation
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


suspend fun getStringFile(f: File): String {
    var inputStream: InputStream? = null
    var encodedFile = ""
    val lastVal: String
    try {
        inputStream = FileInputStream(f.absolutePath)
        val buffer = ByteArray(1024) //specify the size to allow
        var bytesRead: Int
        val output = ByteArrayOutputStream()
        val output64 =
            Base64OutputStream(output, Base64.DEFAULT)
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            output64.write(buffer, 0, bytesRead)
        }
        output64.close()
        encodedFile = output.toString()
    } catch (e1: FileNotFoundException) {
        e1.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    lastVal = "data:image/png;base64," + encodedFile
    return lastVal
}

fun Uri.encode(): String {
    val imagefile = File(this.path)
    var fis: FileInputStream? = null
    try {
        fis = FileInputStream(imagefile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    val bm = BitmapFactory.decodeStream(fis)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return "data:image/png;base64," + Base64.encodeToString(b, Base64.DEFAULT)
}

 fun Bitmap.encodeToBase64Image(): String? {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

@SuppressLint("MissingPermission")
fun GoogleMap.setLocationWithMarker(isDrawMarker:Boolean,latitude: String, longitude: String) {
    var latLng = LatLng(latitude.toDouble(), longitude.toDouble())
    this.clear()
    this.animateCamera(CameraUpdateFactory.zoomIn())
    this.uiSettings.isCompassEnabled = false
    this.uiSettings.isScrollGesturesEnabled = true
    this.isMyLocationEnabled = true
    var camPos = CameraPosition.Builder().target(latLng).zoom(15.5f).build()
    this.animateCamera(CameraUpdateFactory.newCameraPosition(camPos))
    if(isDrawMarker){
        this.addMarker(
            MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
    }
    this.uiSettings.isMyLocationButtonEnabled = true
    this.uiSettings.isMapToolbarEnabled = false
}

fun Context.getCurrentLatLng(getLatLng: (Double, Double) -> Unit) {

    SmartLocation.with(this).location()
        .oneFix()
        .start { myLoc ->
            getLatLng.invoke(myLoc.latitude, myLoc.longitude)
        }
}

fun Fragment.dismissDialogFragment(){
    (childFragmentManager.findFragmentByTag(AppConstants.FRATGMENT_TAG) as? DialogFragment)?.dismiss()
}


fun Location.observeName(context: Context, onGetName: (String) -> Unit) {
    lateinit var geocoder: Geocoder
    var locationName: String = ""
    geocoder = Geocoder(context)
    var addresses: List<Address>? = null
    try {
        addresses = geocoder.getFromLocation(this.latitude, this.longitude, 1)
        var zipCode = ""
        if (addresses != null && addresses.isNotEmpty()) {
            locationName = addresses.get(0).getAddressLine(0)
        }
        onGetName.invoke(locationName)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun EditText.
        textChangeObserve(changedText: (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            changedText.invoke(p0.toString())
        }
    })
}


suspend fun convert(bitmap: Bitmap): String {
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    val imageBytes = baos.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.NO_WRAP)
}


fun Context.checkCallPermission(isGranted:(Boolean) -> Unit) {

    val permissions =
        arrayOf(
            Manifest.permission.CALL_PHONE
        )
    Permissions.check(
        this,
        permissions,
        null/*options*/,
        null,
        object : PermissionHandler() {
            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                isGranted.invoke(false)
            }

            override fun onGranted() {
               isGranted.invoke(true)
            }
        })
}

fun Context.checkMapPermission(isGranted:(Boolean) -> Unit) {

    val permissions =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    Permissions.check(
        this,
        permissions,
        null/*options*/,
        null,
        object : PermissionHandler() {
            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                isGranted.invoke(false)
            }

            override fun onGranted() {
               isGranted.invoke(true)
            }
        })
}

fun getDistance(startLati: Double, startLongi: Double, goalLati: Double, goalLongi: Double): Float {
    val resultArray = FloatArray(99)
    Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray)
    val miles = (resultArray[0] / 1609.344).toFloat()
    val s = String.format("%.2f", miles * 1000)
    return s.toFloat() / 1000
}

fun Context?.getUniqueAndroidId(): String {
    var androidId =
        Settings.Secure.getString(this?.getContentResolver(), Settings.Secure.ANDROID_ID)
    if (androidId.isNullOrEmpty()) {
        val telephonyManager = this?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        androidId = telephonyManager.deviceId
    }
    return androidId
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun EditText.isValidPhone(): Boolean {
    return (!TextUtils.isEmpty(text.toString().trim { it <= ' ' })
            && Patterns.PHONE.matcher(text.toString().trim { it <= ' ' }).matches()
            && text.toString().length >= 13)
}

fun EditText.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches()
}


fun Double.roundTwoDigit(): String {
    return this.toBigDecimal().setScale(2, RoundingMode.UP).toString()
}

infix fun TextView.round(value: Double) {
    this.text = "£" + value.toBigDecimal().setScale(2, RoundingMode.UP).toString()
}

infix fun TextView.round(value: Float) {
    var value = value.toDouble()
    this.text = "" + value.toBigDecimal().setScale(2, RoundingMode.UP).toString()
}


fun EditText.observe(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun CheckBox.observe(isChecked: (Boolean) -> Unit) {
    this.setOnCheckedChangeListener { compoundButton, b ->
        isChecked.invoke(b)
    }
}

fun String?.isProperNotNull(): Boolean {
    return when {
        this == null -> {
            false
        }
        this.equals("null", true) -> {
            false
        }
        else -> this.isNotEmpty()
    }
}

fun EditText.getIntOrZero(): Int {
    var value = 0;
    try {
        value = this.text.toString().replace(",", "").toInt()
    } catch (e: Exception) {
        value = 0
    }
    return value
}


fun String.getIntOrZero(): Int {
    var value = 0;
    try {
        value = this.replace(",", "").toInt()
    } catch (e: Exception) {
        value = 0
    }
    return value
}


fun Int.percent(number: Int): Int {
    val value = (this / 100).times(number)
    return value
}


fun Double.percent(number: Int): Double {
    val value = (this / 100).times(number)
    return value
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}


fun <T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
    val callBackKt = CallBackKt<T>()
    callback.invoke(callBackKt)
    this.enqueue(callBackKt)
}

class CallBackKt<T> : Callback<T> {

    var onSucessResponse: ((Response<T>) -> Unit)? = null
    var onErrorResponse: ((String?) -> Unit)? = null
    var onFailure: ((t: Throwable?) -> Unit)? = null

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailure?.invoke(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            if (response.body() != null) {
                onSucessResponse?.invoke(response)
            } else if (response.errorBody() != null) {
                onErrorResponse?.invoke(response.errorBody()?.string())
            } else {
                onFailure?.invoke(Throwable("No error mentioned"))
            }

        } else {
            onErrorResponse?.invoke(response.errorBody()?.string())
        }
    }

}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}


fun View.toggle() {
    if (this.isVisible) {
        this.gone()
    } else {
        this.visible()
    }
}

fun EditText.setEmpty() {
    this.setText("")
}

fun View.isVisibleToUser(): Boolean {
    return visibility == View.VISIBLE
}

fun EditText.string(): String {
    return this.text.toString().trim()
}

fun String?.requireString(): String {
    return "" + this
}



fun View.takePic() {
    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
        override fun onGranted() {
            CropImage.activity()
                .setCropMenuCropButtonTitle("Done")
                .setActivityTitle("Choose File")
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(context as Activity)
        }

        override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
            super.onDenied(context, deniedPermissions)
        }
    })
}


//
//fun AutoCompleteTextView.findId(allValues:ArrayList<DDItem>):Int
//{
//    var id=1
//   allValues.find { it.name.equals(text.toString(),true) }?.let {
//       id=it.id
//   }
//
//    return id
//
//}


fun Context.newNavigatorIntent(
    latitude: Double,
    longitude: Double,
    name: String
): Intent? {
    val format =
        "geo:0,0?q=" + java.lang.Double.toString(latitude) + "," + java.lang.Double.toString(
            longitude
        ) + "(" + name + ")"
    val uri: Uri = Uri.parse(format)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    return if (intent.resolveActivity(this.packageManager) != null) {
        intent
    } else null
}


fun Context.newDialerIntent(phone: String): Intent? {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    return if (intent.resolveActivity(this.packageManager) != null) {
        intent
    } else null
}


fun Context.newSendEmailsIntent(
    emails: Array<String>?,
    subject: String?,
    text: String?
): Intent? {
    val mailIntent = Intent(Intent.ACTION_SENDTO)
    mailIntent.type = "message/rfc822"
    mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    mailIntent.putExtra(Intent.EXTRA_EMAIL, emails)
    mailIntent.putExtra(Intent.EXTRA_TEXT, text)
    return if (mailIntent.resolveActivity(this.packageManager) != null) {
        mailIntent
    } else null
}

fun Context.newSendEmailIntent(
    email: String,
    subject: String?,
    text: String?
): Intent? {
    return newSendEmailsIntent(arrayOf(email), subject, text)
}

fun Context.callIntent(phoneNumber : String){
    val intent = Intent(
        Intent.ACTION_CALL,
        Uri.parse("tel:" +phoneNumber)
    )
    startActivity(intent)
}
fun Context.newOpenUrlIntent(url: String?): Intent? {
    val urlIntent = Intent(Intent.ACTION_VIEW)
    urlIntent.data = Uri.parse(url)
    return if (urlIntent.resolveActivity(this.packageManager) != null) {
        urlIntent
    } else null
}

fun Context.newShareFileIntent(
    uri: Uri?,
    mimeType: String?
): Intent? {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
    shareIntent.type = mimeType
    return if (shareIntent.resolveActivity(this.packageManager) != null) {
        shareIntent
    } else null
}


fun Context.newShareTextIntent(text: String?): Intent? {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
    shareIntent.type = "text/*"
    return if (shareIntent.resolveActivity(this.packageManager) != null) {
        shareIntent
    } else null
}


fun Activity.openActivity(clazz: Class<out Activity>) {
    startActivity(Intent(this, clazz))
}

fun Activity.openActivityWithExist(clazz: Class<out Activity>) {
    startActivity(Intent(this, clazz))
    this.finish()
}


fun Activity.openActivityForResult(clazz: Class<out Activity>, requestCode: Int) {
    startActivityForResult(Intent(this, clazz), requestCode)
}


fun getPriceList():ArrayList<String>{
    var price: ArrayList<String> = ArrayList()
    price.clear()
    price.add("£")
    price.add("££")
    price.add("£££")
    price.add("££££")
    return  price
}

fun Fragment.showProgress(show: Boolean){
    var dialog: androidx.appcompat.app.AlertDialog
    dialog = DialogUtils.getProgressDialog(requireContext())
    if (dialog != null && show) {
        if (!dialog.isShowing)
            dialog.apply {
                show()
            }
    } else if (dialog != null && !show) {
        if (dialog.isShowing)
            dialog.dismiss()
    }
}




fun View.collapse() {
    val animate = TranslateAnimation(
        0f,
        0f,
        0f,
        this.height.toFloat()
    )
    animate.duration = 500
    animate.fillAfter = true
    this.startAnimation(animate)
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {
            this@collapse.gone()
        }

        override fun onAnimationStart(animation: Animation?) {

        }
    })
}


fun View.expand() {
    val animate = TranslateAnimation(
        0f,
        0f,
        this.height.toFloat(),
        0f
    )
    animate.duration = 500
    animate.fillAfter = true
    this.startAnimation(animate)
    animate.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {

        }

        override fun onAnimationStart(animation: Animation?) {
            this@expand.visible()
        }
    })
}


fun View.showSnackBar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.show()
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}


fun Fragment.getSimpleName(): String {
    return this.javaClass.simpleName
}

fun Fragment.getColorCustom(color: Int): Int {
    return ContextCompat.getColor(requireContext(), color)
}


fun Date.dateWithUTCTimeZone(): String {
    val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.format(this)
}

fun String.getDateWithCurrentTimeZone(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    val date = sdf.parse(this)
    sdf.setTimeZone(TimeZone.getDefault())
    return "" + date?.dateWithCurrentTimeZone()
}

fun Date.dateWithCurrentTimeZone(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getDefault());
    return sdf.format(this)
}


fun Fragment.getColorToLoad(id: Int): Int {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        requireContext().getColor(id)
    } else {
        requireContext().resources.getColor(id)
    }
}


fun EditText.getString(): String {
    if (this.text.isNullOrEmpty()) {
        return ""
    } else {
        return this.text.toString()
    }
}

fun EditText.getInt(): Int {
    var value = 0
    try {
        value = this.text.toString().toInt()
    } catch (e: Exception) {
    }
    return value
}


infix fun View.color(colorString: String) {
    if (this is TextView) {
        this.setTextColor(Color.parseColor(colorString))
    } else if (this is EditText) {
        this.setTextColor(Color.parseColor(colorString))
    }
}

infix fun View.color(colorInt: Int) {
    if (this is TextView) {
        this.setTextColor(colorInt)
    } else if (this is EditText) {
        this.setTextColor(colorInt)
    }
}


fun Spinner.setDropDownAdapter(list: ArrayList<String>) {

    val ddAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, list)
    this.adapter = ddAdapter
}

inline fun RecyclerView.setAnyAdapter(
    adapter: BaseAdapter?,
    isGridLayoutManager: Boolean = false,
    count: Int = 0
) {
    if (isGridLayoutManager) {
        this.layoutManager = GridLayoutManager(context, count)
    } else {
        this.layoutManager = LinearLayoutManager(context)
    }
    this.adapter = adapter
}








