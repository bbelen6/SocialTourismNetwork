package com.miwfem.socialtourismnetwork.presentation.ui.home.view

import android.util.Log
import android.view.View
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentHomeBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun setUpDataBinding(view: View) {
        homeBinding = FragmentHomeBinding.bind(view)
    }

    override fun getBundleExtras() {
        //TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        with(homeViewModel) {
            posts.observe(viewLifecycleOwner, {
                Log.d("POST", "POST")
            })
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}