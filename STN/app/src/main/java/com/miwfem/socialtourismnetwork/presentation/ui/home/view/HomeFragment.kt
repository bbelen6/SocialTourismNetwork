package com.miwfem.socialtourismnetwork.presentation.ui.home.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentHomeBinding
import com.miwfem.socialtourismnetwork.databinding.ItemSeeAllPostBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.adapter.PostAdapter
import com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces.ItemPostListener
import com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel.HomeViewModel
import com.miwfem.socialtourismnetwork.utils.USER
import kotlinx.android.synthetic.main.item_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home), ItemPostListener {

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var dialog: Dialog
    private var logUser: String? = null

    override fun setUpDataBinding(view: View) {
        homeBinding = FragmentHomeBinding.bind(view)
    }

    override fun getBundleExtras() {
        arguments?.let {
            logUser = it.getString(USER)
        }
    }

    override fun observeViewModel() {
        with(homeViewModel) {
            posts.observe(viewLifecycleOwner, {
                setPostsAdapter(it)
            })
        }
    }

    private fun setPostsAdapter(posts: List<PostVO>) {
        val adapter = PostAdapter(posts, this@HomeFragment, logUser)
        with(homeBinding) {
            rvPosts.itemAnimator = DefaultItemAnimator()
            rvPosts.adapter = adapter
        }
    }

    override fun deletePost(post: PostVO) {
        showDeletePostAlert(post)
    }

    override fun addFavPost(post: PostVO) {
        Log.d("POST", "POST")
    }

    override fun seeAllPost(post: PostVO) {
        showSeeAllPostDialog(post)
    }

    private fun showSeeAllPostDialog(post: PostVO) {
        dialog = Dialog(requireContext(), R.style.DialogTheme)
        val dialogBinding: ItemSeeAllPostBinding =
            ItemSeeAllPostBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.apply {
            seeAllComment.text = post.comment
            seeAllLocation.text = post.location
            seeAllUser.text = post.user
            seeAllCategory.text = post.category
            seeAllClose.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
        dialog.setContentView(dialogBinding.root)
    }

    private fun showDeletePostAlert(post: PostVO) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                homeViewModel.deletePost(post)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        fun newInstance(user: String?) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
            }
        }
    }

}