package com.miwfem.socialtourismnetwork.presentation.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentProfileBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.profile.viewmodel.ProfileViewModel
import com.miwfem.socialtourismnetwork.utils.PREFERENCES_FILE
import com.miwfem.socialtourismnetwork.utils.ResultType
import com.miwfem.socialtourismnetwork.utils.USER
import com.miwfem.socialtourismnetwork.utils.USER_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var profileBinding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModel()
    private var user: String? = null
    private var userName: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun setUpDataBinding(view: View) {
        profileBinding = FragmentProfileBinding.bind(view).apply {
            profileMail.text = user
            profileName.text = userName
            editProfile.setOnClickListener {
                editView(true)
            }
            saveProfile.setOnClickListener {
                if (editProfileName.text.isNotEmpty())
                    saveProfile(editProfileName.text.toString())
                else
                    editProfileName.error = getString(R.string.fill_error)
            }
            editProfileClose.setOnClickListener {
                editView(false)
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
    }

    override fun observeViewModel() {
        //TODO("Not yet implemented")
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
                        changeSharedPreferences(newName)
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

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
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