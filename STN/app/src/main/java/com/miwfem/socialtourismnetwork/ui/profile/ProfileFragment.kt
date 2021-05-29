package com.miwfem.socialtourismnetwork.ui.profile

import android.view.View
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentProfileBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var profileBinding: FragmentProfileBinding

    override fun setUpDataBinding(view: View) {
        profileBinding = FragmentProfileBinding.bind(view)
    }

    override fun getBundleExtras() {
        //TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}