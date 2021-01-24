package com.preach.mentaldisorder.Views.fragments

import com.preach.mentaldisorder.R
import kotlinx.android.synthetic.main.fragment_computational_form.*

class ComputationalFormFragment : BaseFragment(R.layout.fragment_computational_form) {
    override fun initViews() {
        btnComputationalBack.setOnClickListener {
            onBackPress()
        }
    }

    override fun attachViewModel() {

    }
}