package com.preach.tawridcustomer.utils.generalUtils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class ApplicationRunUtils {

    companion object {
        fun forceRunApp(context: Activity?) {
            var context = context
            var activity : AppCompatActivity ? = null

            if(activity==null)
            {
                activity=context as AppCompatActivity
            }

            val launchIntent =
                context?.packageManager?.getLaunchIntentForPackage(context.packageName)
            launchIntent!!.flags = (Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                    or Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(launchIntent)
            activity?.finish()
        }
    }
}