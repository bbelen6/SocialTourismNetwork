package com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.*
import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val manageFavoriteUseCase: ManageFavoriteUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _posts by lazy { MutableLiveData<List<PostVO>>() }
    val posts: LiveData<List<PostVO>>
        get() = _posts

    private val _categories by lazy { MutableLiveData<List<CategoryVO>>() }
    val categories: LiveData<List<CategoryVO>>
        get() = _categories

    private val _locations by lazy { MutableLiveData<List<LocationVO>>() }
    val location: LiveData<List<LocationVO>>
        get() = _locations

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

    fun getLocations() {
        viewModelScope.launch {
            _locations.value = getLocationsUseCase.execute().data?.map()
        }
    }

    fun sendMessage(message: MessageVO) {
        sendMessageUseCase.execute(SendMessageUseCase.Params(message.map()))
    }
}
