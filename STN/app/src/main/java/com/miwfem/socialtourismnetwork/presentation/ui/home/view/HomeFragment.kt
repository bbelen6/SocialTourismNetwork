package com.miwfem.socialtourismnetwork.presentation.ui.home.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentHomeBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.adapter.PostAdapter
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
                setPostsAdapter(it)
            })
        }
    }

    private fun setPostsAdapter(posts: List<PostVO>) {
        with(homeBinding) {
            val lManager = LinearLayoutManager(context)
            rvPosts.layoutManager = lManager
            rvPosts.adapter = PostAdapter(posts)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}