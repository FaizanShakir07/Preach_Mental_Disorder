package com.preach.mentaldisorder.Views.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.afollestad.vvalidator.form
import com.preach.mentaldisorder.Models.Source.Repository.UserDataSource
import com.preach.mentaldisorder.R
import com.preach.mentaldisorder.Utils.Application.showToast
import com.preach.mentaldisorder.Utils.GeneralUtils.DialogUtils


abstract class BaseDialog(): DialogFragment() {


    lateinit var dialog: androidx.appcompat.app.AlertDialog
    @SuppressLint("ResourceAsColor")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = DialogUtils.getProgressDialog(requireContext())
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
        val builder = AlertDialog.Builder(requireContext(), R.style.DialogStyle)
        builder.setCancelable(false)
        val inflater = requireActivity().layoutInflater
        val dialog = inflater.inflate(getLayoutId(), null)
        initViews(dialog)
        builder.setView(dialog)
        return builder.create()
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
                                showToast("Some selections are missing")
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



    abstract fun initViews(view: View)

    abstract fun getLayoutId():Int

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val windowParams = window?.attributes
        windowParams?.dimAmount = 0.8f
        window?.attributes = windowParams
    }




}