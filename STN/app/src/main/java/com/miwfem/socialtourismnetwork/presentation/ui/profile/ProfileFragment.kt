package com.miwfem.socialtourismnetwork.presentation.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentProfileBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.hideKeyboard
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.adapter.PostAdapter
import com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces.ItemPostListener
import com.miwfem.socialtourismnetwork.presentation.ui.profile.viewmodel.ProfileViewModel
import com.miwfem.socialtourismnetwork.utils.PREFERENCES_FILE
import com.miwfem.socialtourismnetwork.utils.ResultType
import com.miwfem.socialtourismnetwork.utils.USER
import com.miwfem.socialtourismnetwork.utils.USER_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile), ItemPostListener {

    private lateinit var profileBinding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModel()
    private var user: String? = null
    private var userName: String? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var postsAdapter: PostAdapter? = null

    override fun setUpDataBinding(view: View) {
        profileBinding = FragmentProfileBinding.bind(view).apply {
            profileMail.text = user
            profileName.text = userName
            editProfile.setOnClickListener {
                editView(true)
            }
            saveProfile.setOnClickListener {
                when (editProfileName.text.toString()) {
                    userName -> {
                        editProfileName.error = getString(R.string.same_name)
                    }
                    "" -> {
                        editProfileName.error = getString(R.string.fill_error)
                    }
                    else -> {
                        saveProfile(editProfileName.text.toString())
                    }
                }
            }
            editProfileClose.setOnClickListener {
                editView(false)
                hideKeyboard()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = requireActivity().getSharedPreferences(
            PREFERENCES_FILE,
            Context.MODE_PRIVATE
        )
    }

    override fun getBundleExtras() {
        arguments?.let {
            user = it.getString(USER)
            userName = it.getString(USER_NAME)
        }
        profileViewModel.getPosts(user)
    }

    override fun observeViewModel() {
        with(profileViewModel) {
            posts.observe(viewLifecycleOwner, {
                setPostsAdapter(it)
            })
        }
    }

    private fun setPostsAdapter(posts: List<PostVO>) {
        if (postsAdapter == null) {
            postsAdapter = PostAdapter(this@ProfileFragment, user)
        }
        with(profileBinding) {
            if (rvProfile.adapter == null) {
                rvProfile.adapter = postsAdapter
            }
            postsAdapter?.addDataSet(posts)
        }
    }

    private fun editView(visible: Boolean) {
        with(profileBinding) {
            editProfileName.isVisible = visible
            saveProfile.isVisible = visible
            editProfile.isVisible = !visible
            editProfileClose.isVisible = visible
        }
    }

    private fun saveProfile(newName: String) {
        user?.let {
            profileViewModel.saveName(it, newName).also { result ->
                when (result) {
                    ResultType.SUCCESS -> {
                        editView(false)
                        profileBinding.profileName.text = newName
                        profileBinding.editProfileName.setText("")
                        changeSharedPreferences(newName)
                        hideKeyboard()
                        showToast(getString(R.string.changed_name))
                    }
                    ResultType.ERROR -> {
                        showToast(getString(R.string.changed_name_error))
                    }
                }
            }
        }
    }

    private fun changeSharedPreferences(newName: String) {
        userName = newName
        val sharedEdit = sharedPreferences.edit()
        sharedEdit?.putString(USER_NAME, newName)
        sharedEdit.apply()
    }

    override fun deletePost(post: PostVO) {
        showDeletePostAlert(post)
    }

    override fun addFavPost(post: PostVO) {
        profileViewModel.manageFavorite(post, user)
    }

    override fun seeAllPost(post: PostVO) {
        showSeeAllPostDialog(post, user)
    }

    private fun showDeletePostAlert(post: PostVO) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                profileViewModel.deletePost(post).also { result ->
                    when (result) {
                        ResultType.SUCCESS -> {
                            showToast(getString(R.string.delete_post))
                        }
                        ResultType.ERROR -> {
                            showToast(getString(R.string.delete_post_error))
                        }
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        fun newInstance(user: String?, userName: String?) = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
                putString(USER_NAME, userName)
            }
        }
    }

}