package com.miwfem.socialtourismnetwork.presentation.ui.home.view

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentHomeBinding
import com.miwfem.socialtourismnetwork.databinding.ItemSeeAllPostBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.adapter.PostAdapter
import com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces.ItemPostListener
import com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home), ItemPostListener {

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var dialog: Dialog

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
            rvPosts.adapter = PostAdapter(posts, this@HomeFragment)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun deletePost(post: PostVO) {
        Log.d("POST", "POST")
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
}