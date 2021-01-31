package com.preach.mentaldisorder.Views.fragments

import com.preach.mentaldisorder.R
import kotlinx.android.synthetic.main.fragment_goal_result.*

class GoalResultFragment: BaseFragment(R.layout.fragment_goal_result) {
    override fun initViews() {
        ivBack.setOnClickListener {
            onBackPress()
        }
    }

    override fun attachViewModel() {

    }
}