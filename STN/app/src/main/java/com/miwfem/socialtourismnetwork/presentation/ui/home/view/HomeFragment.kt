package com.miwfem.socialtourismnetwork.presentation.ui.home.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentHomeBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.hideKeyboard
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.adapter.PostAdapter
import com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces.ItemPostListener
import com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel.HomeViewModel
import com.miwfem.socialtourismnetwork.utils.ResultType
import com.miwfem.socialtourismnetwork.utils.USER
import kotlinx.android.synthetic.main.item_post.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home), ItemPostListener {

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var dialog: Dialog
    private var logUser: String? = null

    override fun setUpDataBinding(view: View) {
        homeBinding = FragmentHomeBinding.bind(view).apply {
            filterButton.setOnClickListener {
                showFilters(true)
            }
            closeFilter.setOnClickListener {
                showFilters(false)
            }
            applyFiltersButton.setOnClickListener {
                applyFilters(
                    searchLocationFilter.selectedItem.toString(),
                    searchAreaFilter.selectedItem.toString(),
                    searchCategoryFilter.selectedItem.toString(),
                    searchUserFilter.text.toString()
                )
            }
            deleteFiltersButton.setOnClickListener {
                deleteFilters()
            }
            filterButton.isVisible = logUser != null
        }
    }


    override fun getBundleExtras() {
        arguments?.let {
            logUser = it.getString(USER)
        }
        homeViewModel.getPosts(logUser)
        if (logUser != null) {
            homeViewModel.getCategories()
            homeViewModel.getLocations()
        }
    }

    fun refreshHome(user: String?) {
        logUser = user
        homeBinding.filterButton.isVisible = logUser != null
        homeViewModel.getPosts(logUser)
        if (logUser != null) {
            homeViewModel.getCategories()
            homeViewModel.getLocations()
        }
        this.hideKeyboard()
    }

    override fun observeViewModel() {
        with(homeViewModel) {
            posts.observe(viewLifecycleOwner, {
                setPostsAdapter(it)
            })
            categories.observe(viewLifecycleOwner, {
                setCategoriesSpinner(it)
            })
            location.observe(viewLifecycleOwner, {
                setLocationsSpinner(it)
            })
        }
    }

    private fun setPostsAdapter(posts: List<PostVO>) {
        val adapter = PostAdapter(this@HomeFragment, logUser)
        with(homeBinding) {
            rvPosts.itemAnimator = DefaultItemAnimator()
            rvPosts.adapter = adapter
            adapter.addDataSet(posts)
        }
    }

    private fun setCategoriesSpinner(categories: List<CategoryVO>) {
        with(homeBinding) {
            val allCategories = mutableListOf(getString(R.string.categories))
            allCategories.addAll(categories.map { it.name })
            searchCategoryFilter.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                allCategories
            )
        }
    }

    private fun setLocationsSpinner(locations: List<LocationVO>) {
        with(homeBinding) {
            val allLocations = mutableListOf(getString(R.string.filter_location))
            allLocations.addAll(locations.map { it.name })
            searchLocationFilter.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                allLocations
            )
            searchLocationFilter.setTitle(getString(R.string.filter_location))
            searchLocationFilter.setPositiveButton(getString(R.string.close))
            val areas = mutableListOf(getString(R.string.filter_area))
            areas.addAll(locations.map { it.areaName })
            searchAreaFilter.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                areas.distinct()
            )
            searchAreaFilter.setTitle(getString(R.string.filter_area))
            searchAreaFilter.setPositiveButton(getString(R.string.close))
        }
    }

    override fun deletePost(post: PostVO) {
        showDeletePostAlert(post)
    }

    override fun addFavPost(post: PostVO) {
        homeViewModel.manageFavorite(post, logUser)
    }

    override fun seeAllPost(post: PostVO) {
        showSeeAllPostDialog(post, logUser)
    }
/*
    private fun showSeeAllPostDialog(post: PostVO) {
        dialog = Dialog(requireContext(), R.style.DialogTheme)
        val dialogBinding: ItemSeeAllPostBinding =
            ItemSeeAllPostBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.apply {
            seeAllComment.text = post.comment
            seeAllLocation.text = post.location
            seeAllUser.text = if (post.userName.isNotEmpty()) post.userName else post.user
            seeAllCategory.text = post.category
            deletePost2.isVisible = false
            favoritePost2.isVisible = logUser != null

            if (post.isFav) {
                favoritePost2.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                favoritePost2.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            seeAllClose.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
        dialog.setContentView(dialogBinding.root)
    }*/

    private fun showFilters(visibility: Boolean) {
        with(homeBinding) {
            filterButton.isVisible = !visibility
            filterLayout.isVisible = visibility
            if (!visibility) this@HomeFragment.hideKeyboard()
        }
    }

    private fun applyFilters(
        location: String?,
        area: String,
        category: String,
        user: String
    ) {
        val finalPosts = mutableListOf<PostVO>()
        homeViewModel.posts.value?.forEach { post ->
            if ((location == post.location || location == getString(R.string.filter_location)) &&
                (area == post.area || area == getString(R.string.filter_area)) &&
                (post.category.contains(category) || category == getString(R.string.categories)) &&
                (post.userName.contains(user) || user == "")
            ) {
                finalPosts.add(post)
            }
        }
        if (finalPosts.isNotEmpty()) {
            showFilters(false)
            (homeBinding.rvPosts.adapter as? PostAdapter)?.addDataSet(finalPosts)
        } else showToast("No hay coincidencias con los filtros")
    }

    private fun deleteFilters() {
        with(homeBinding) {
            searchCategoryFilter.setSelection(0)
            searchLocationFilter.setSelection(0)
            searchAreaFilter.setSelection(0)
            searchUserFilter.setText("")
        }
        homeViewModel.posts.value?.let {
            (homeBinding.rvPosts.adapter as? PostAdapter)?.addDataSet(
                it
            )
        }
    }

    private fun showDeletePostAlert(post: PostVO) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                homeViewModel.deletePost(post).also { result ->
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
        fun newInstance(user: String?) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(USER, user)
            }
        }
    }

}