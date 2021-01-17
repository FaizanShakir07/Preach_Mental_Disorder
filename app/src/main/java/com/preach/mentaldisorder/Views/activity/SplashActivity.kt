package com.smartserve.consumer.Views.activities

import android.Manifest
import android.content.Context
import android.os.Handler
import androidx.lifecycle.Observer
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.preach.mentaldisorder.R
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    companion object {
        val SPLASH_DELAY: Long = 3000
    }

    override fun initViews() {
        attachViewModel()
        Handler().postDelayed(Runnable {
            gotoRegActivity()
        }, SPLASH_DELAY)
    }

    fun attachViewModel(){

    }
    override fun getLayoutId(): Int {
       return R.layout.activity_splash
    }


}
