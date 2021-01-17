package com.preach.mentaldisorder.Views.fragments

import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.afollestad.vvalidator.form
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.preach.mentaldisorder.Models.Source.Repository.UserDataSource
import com.preach.mentaldisorder.R
import com.preach.mentaldisorder.Utils.Application.showToast
import com.preach.mentaldisorder.Utils.GeneralUtils.DialogUtils
import com.preach.mentaldisorder.Utils.GeneralUtils.SessionManager
import com.preach.mentaldisorder.ViewModels.BaseAndroidViewModel
import com.smartserve.consumer.Views.activities.RegistrationActivity
import org.koin.android.ext.android.get
import java.util.*


abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    abstract fun initViews()

    abstract fun attachViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachViewModel()
        initViews()
    }


    fun applyValidations(
        viewList: ArrayList<View>,
        buttonView: View,
        callback: UserDataSource.OnValidationCallback
    ) {
        form {
            for (view in viewList) {
                if (view is EditText) {
                    input(view) {
                        isNotEmpty().description("Field Required!!")
                    }
                } else if (view is Spinner) {
                    spinner(view) {
                        selection().greaterThan(0)
                        onErrors { view, errors ->
                            if (errors.isNotEmpty()) {
                                showToast("Something is mising")
                            }
                        }
                    }
                }
            }

            submitWith(buttonView) { result ->
                var results: ArrayList<String> = ArrayList()
                for (value in result.values()) {
                    results.add(value.asString())
                }
                callback.onApplied(true, results)
            }
        }
    }

    fun navigateToFragment(action: Int, bundle: Bundle?= null) {

        val navController =
            Navigation.findNavController(activity as Activity, R.id.nav_reg_host_fragment)
        navController.navigate(action, bundle)
    }


    protected fun setupGeneralViewModel(generalViewModel: BaseAndroidViewModel) {

        with(generalViewModel)
        {
            snackbarMessage.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {

                }
            })

            progressBar.observe(viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {

                }
            })
        }
    }

    protected fun onBackPress() {
        requireActivity().onBackPressed()
    }



}
