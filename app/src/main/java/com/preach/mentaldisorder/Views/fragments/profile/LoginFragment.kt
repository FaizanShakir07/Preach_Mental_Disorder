package com.preach.mentaldisorder.Views.fragments.profile

import android.content.Intent
import com.google.android.material.tabs.TabLayout
import com.preach.mentaldisorder.R
import com.preach.mentaldisorder.Utils.Application.gone
import com.preach.mentaldisorder.Utils.Application.visible
import com.preach.mentaldisorder.Views.fragments.BaseFragment
import com.smartserve.consumer.Views.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(R.layout.fragment_login) {
    override fun initViews() {
        setTabLayout()
        btnLogin.setOnClickListener {
            var intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        btnRegister.setOnClickListener {
            var intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    btnRegister.gone()
                    llRegister.gone()
                    btnLogin.visible()
                    llLogin.visible()
                } else if (tab?.position == 1) {
                    btnLogin.gone()
                    llLogin.gone()
                    btnRegister.visible()
                    llRegister.visible()
                }
            }
        })
    }

    override fun attachViewModel() {

    }
}