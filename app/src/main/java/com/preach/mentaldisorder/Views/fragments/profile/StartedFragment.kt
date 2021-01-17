package com.preach.mentaldisorder.Views.fragments.profile

import android.view.View
import com.preach.mentaldisorder.R
import com.preach.mentaldisorder.Views.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_started.*

class StartedFragment : BaseFragment(R.layout.fragment_started), View.OnClickListener {
    override fun initViews() {
        btnGettingStarted.setOnClickListener(this)
        btnAsAGuest.setOnClickListener(this)
    }

    override fun attachViewModel() {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnAsAGuest -> {
                navigateToFragment(R.id.action_startedFragment_to_loginFragment)
            }
            btnGettingStarted -> {
                navigateToFragment(R.id.action_startedFragment_to_loginFragment)
            }
        }
    }
}