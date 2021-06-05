package com.miwfem.socialtourismnetwork.presentation.ui.addPost.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAddPostBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel.AddPostViewModel
import com.miwfem.socialtourismnetwork.presentation.utils.setBoldText
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {

    private lateinit var addPostBinding: FragmentAddPostBinding
    private var user: String? = null
    private val categories = listOf("Restaurantes", "Eventos", "Cultura", "Otros")
    private val addPostViewModel: AddPostViewModel by viewModel()

    override fun setUpDataBinding(view: View) {
        addPostBinding = FragmentAddPostBinding.bind(view).apply {
            categorySelector.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
            )
            locationSelector.setTitle(getString(R.string.location))
            locationSelector.setPositiveButton(getString(R.string.close))
            savePostButton.setOnClickListener {
                //TODO: SAVE POST IN FIREBASE
                if (addPostEdit.text.isEmpty()) addPostEdit.error =
                    getString(R.string.add_post_error)
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