package com.smartserve.consumer.Views.activities

import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.firebase.iid.FirebaseInstanceId
import com.preach.mentaldisorder.R
import com.preach.mentaldisorder.Utils.GeneralUtils.AppConstants.Companion.REQUEST_CODES
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun getLayoutId(): Int {
        return R.layout.activity_registration
    }

    override fun initViews() {

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_reg_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(REQUEST_CODES.contains(requestCode)) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_reg_host_fragment)
            val childFragments = navHostFragment?.childFragmentManager?.fragments
            childFragments?.forEach { it.onActivityResult(requestCode, resultCode, data) }
        }
    }



}
