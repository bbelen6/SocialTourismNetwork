package com.miwfem.socialtourismnetwork.ui.home

import android.view.View
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentHomeBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun setUpDataBinding(view: View) {
        homeBinding = FragmentHomeBinding.bind(view)
    }

    override fun getBundleExtras() {
        //TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}