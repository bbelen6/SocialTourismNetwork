package com.miwfem.socialtourismnetwork.presentation.ui.addPost.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAddPostBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel.AddPostViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.presentation.utils.setBoldText
import com.miwfem.socialtourismnetwork.utils.ResultType
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {

    private lateinit var addPostBinding: FragmentAddPostBinding
    private var user: String? = null
    private val addPostViewModel: AddPostViewModel by viewModel()

    override fun setUpDataBinding(view: View) {
        addPostBinding = FragmentAddPostBinding.bind(view).apply {
            locationSelector.setTitle(getString(R.string.location))
            locationSelector.setPositiveButton(getString(R.string.close))
            savePostButton.setOnClickListener {
                if (addPostEdit.text.isEmpty()) addPostEdit.error =
                    getString(R.string.add_post_error)
                else {
                    val locationSelected =
                        addPostViewModel.getLocationByPosition(locationSelector.selectedItemPosition)
                    locationSelected?.let { location ->
                        addPostViewModel.savePost(
                            PostVO(
                                user = user ?: "",
                                location = location.name,
                                area = location.areaName,
                                category = categorySelector.selectedItem.toString(),
                                comment = addPostEdit.text.toString(),
                            )
                        ).also { result ->
                            when (result) {
                                ResultType.SUCCESS -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Gracias por el post!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navigateToHome()
                                }
                                ResultType.ERROR -> {
                                    Toast.makeText(
                                        requireContext(),
                                        "Se ha producido un error al añadir el post, intentelo de nuevo",
                                        Toast.LENGTH_SHORT
                                    ).show()
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

    companion object {
        fun newInstance(user: String?) = AddPostFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
            }
        }

        private const val USER = "user"
    }

}