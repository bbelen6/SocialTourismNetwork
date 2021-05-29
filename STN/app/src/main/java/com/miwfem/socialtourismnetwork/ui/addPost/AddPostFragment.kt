package com.miwfem.socialtourismnetwork.ui.addPost

import android.os.Bundle
import android.view.View
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAddPostBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {

    private lateinit var addPostBinding: FragmentAddPostBinding
    private var user: String? = null

    override fun setUpDataBinding(view: View) {
        addPostBinding = FragmentAddPostBinding.bind(view)
    }

    override fun getBundleExtras() {
        arguments?.let {
            user = it.getString(USER)
        }
    }

    companion object {
        fun newInstance(user: String?) = AddPostFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
            }
        }

        private const val USER = "user"
    }

}