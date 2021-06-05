package com.miwfem.socialtourismnetwork.presentation.ui.settings

import android.view.View
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentSettingsBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private lateinit var settingsBinding: FragmentSettingsBinding

    override fun setUpDataBinding(view: View) {
        settingsBinding = FragmentSettingsBinding.bind(view)
    }

    override fun getBundleExtras() {
        //TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        //TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}