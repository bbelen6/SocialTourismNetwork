package com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.DeletePostUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetPostsUseCase
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _posts by lazy { MutableLiveData<List<PostVO>>() }
    val posts: LiveData<List<PostVO>>
        get() = _posts

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            _posts.value = getPostsUseCase.execute().data?.map()
        }
    }

    fun deletePost(post: PostVO): ResultType {
        val newPosts = _posts.value?.toMutableList()
        newPosts?.remove(post)
        _posts.value = newPosts
        return deletePostUseCase.execute(DeletePostUseCase.Params(post.map()))
    }
}
