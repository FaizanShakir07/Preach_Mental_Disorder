package com.smartserve.consumer.Views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.preach.mentaldisorder.Utils.Application.openActivityWithExist

abstract class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.MODE_NIGHT_YES
        setContentView(getLayoutId())

        initViews()
    }


    abstract fun getLayoutId():Int
    abstract fun initViews()




    fun gotoRegActivity()
    {
        openActivityWithExist(RegistrationActivity::class.java)
    }

}
