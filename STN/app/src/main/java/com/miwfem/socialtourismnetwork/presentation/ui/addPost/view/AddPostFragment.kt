package com.miwfem.socialtourismnetwork.presentation.ui.addPost.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAddPostBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.common.setBoldText
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel.AddPostViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.OTHER
import com.miwfem.socialtourismnetwork.utils.ResultType
import com.miwfem.socialtourismnetwork.utils.USER
import com.miwfem.socialtourismnetwork.utils.USER_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {

    private lateinit var addPostBinding: FragmentAddPostBinding
    private var user: String? = null
    private var userName: String? = null
    private val addPostViewModel: AddPostViewModel by viewModel()

    override fun setUpDataBinding(view: View) {
        addPostBinding = FragmentAddPostBinding.bind(view).apply {
            locationSelector.setTitle(getString(R.string.location))
            locationSelector.setPositiveButton(getString(R.string.close))
            categorySelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    addPostViewModel.getCategoryByPosition(position)?.also { category ->
                        categoryOtherInputLayout.isVisible = category.name == OTHER
                    }
                }
            }
            savePostButton.setOnClickListener {
                if (addPostEdit.text.isEmpty()) addPostEdit.error =
                    getString(R.string.add_post_error)
                else if (categoryOtherInputLayout.isVisible && categoryOther.text.isEmpty()) {
                    categoryOther.error = getString(R.string.add_post_error)
                } else {
                    val locationSelected =
                        addPostViewModel.getLocationByPosition(locationSelector.selectedItemPosition)
                    locationSelected?.let { location ->
                        var category = categorySelector.selectedItem.toString()
                        if (categoryOtherInputLayout.isVisible) {
                            category += " - ${categoryOther.text}"
                        }
                        addPostViewModel.savePost(
                            PostVO(
                                user = user ?: "",
                                userName = userName ?: "",
                                location = location.name,
                                area = location.areaName,
                                category = category,
                                comment = addPostEdit.text.toString(),
                            )
                        ).also { result ->
                            when (result) {
                                ResultType.SUCCESS -> {
                                    showToast(getString(R.string.thanks_new_post))
                                    navigateToHome()
                                }
                                ResultType.ERROR -> {
                                    showToast(getString(R.string.error_new_post))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getBundleExtras() {
        arguments?.let {
            user = it.getString(USER)
            userName = it.getString(USER_NAME)
        }
    }

    override fun observeViewModel() {
        with(addPostViewModel) {
            locations.observe(viewLifecycleOwner, { locations ->
                with(addPostBinding) {
                    locationSelector.adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        locations.map { "${it.name} - ${it.areaName}".setBoldText(listOf(it.name)) }
                    )
                }
            })
            categories.observe(viewLifecycleOwner, { categories ->
                with(addPostBinding) {
                    categorySelector.adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        categories.map { it.name }
                    )
                }
            })
        }
    }

    private fun navigateToHome() {
        (requireActivity() as? MainActivity)?.navigateToHome()
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        fun newInstance(user: String?, userName: String?) = AddPostFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
                putString(USER_NAME, userName)
            }
        }
    }

}