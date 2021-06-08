package com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.DeletePostUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetCategoriesUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetPostsUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.ManageFavoriteUseCase
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val manageFavoriteUseCase: ManageFavoriteUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _posts by lazy { MutableLiveData<List<PostVO>>() }
    val posts: LiveData<List<PostVO>>
        get() = _posts

    private val _categories by lazy { MutableLiveData<List<CategoryVO>>() }
    val categories: LiveData<List<CategoryVO>>
        get() = _categories

    fun getPosts(logUser: String?) {
        viewModelScope.launch {
            _posts.value = getPostsUseCase.execute(GetPostsUseCase.Params(logUser)).data?.map()
        }
    }

    fun deletePost(post: PostVO): ResultType {
        val newPosts = _posts.value?.toMutableList()
        newPosts?.remove(post)
        _posts.value = newPosts
        return deletePostUseCase.execute(DeletePostUseCase.Params(post.map()))
    }

    fun manageFavorite(post: PostVO, logUser: String?): ResultType {
        return manageFavoriteUseCase.execute(ManageFavoriteUseCase.Params(post.map(), logUser))
    }

    fun getCategories() {
        viewModelScope.launch {
            _categories.value = getCategoriesUseCase.execute().data?.map()
        }
    }
}
