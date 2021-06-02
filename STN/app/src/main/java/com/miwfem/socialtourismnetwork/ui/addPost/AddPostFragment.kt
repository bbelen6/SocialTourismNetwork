package com.miwfem.socialtourismnetwork.ui.addPost

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAddPostBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment

class AddPostFragment : BaseFragment(R.layout.fragment_add_post) {

    private lateinit var addPostBinding: FragmentAddPostBinding
    private var user: String? = null
    private val categories = listOf("Restaurantes", "Eventos", "Cultura", "Otros")
    private val locations = listOf("Madrid", "Getafe", "Leganes", "Alcalá")

    override fun setUpDataBinding(view: View) {
        addPostBinding = FragmentAddPostBinding.bind(view).apply {
            categorySelector.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
            )
            locationSelector.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                locations
            )
            locationSelector.setTitle(getString(R.string.location))
            savePostButton.setOnClickListener {
                //TODO: SAVE POST IN FIREBASE
            }
        }
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