package com.miwfem.socialtourismnetwork.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetPostsUseCase
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPostsUseCase: GetPostsUseCase
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
}
