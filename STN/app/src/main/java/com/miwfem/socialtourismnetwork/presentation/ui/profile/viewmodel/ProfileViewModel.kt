package com.miwfem.socialtourismnetwork.presentation.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.businesslogic.usecase.DeletePostUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetPostsUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.ManageFavoriteUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SaveUserProfileUseCase
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val manageFavoriteUseCase: ManageFavoriteUseCase
) : ViewModel() {

    private val _posts by lazy { MutableLiveData<List<PostVO>>() }
    val posts: LiveData<List<PostVO>>
        get() = _posts

    fun saveName(user: String, userName: String): ResultType {
        return saveUserProfileUseCase.execute(
            SaveUserProfileUseCase.Params(
                UserEntity(
                    user,
                    userName
                )
            )
        )
    }

    fun getPosts(logUser: String?) {
        viewModelScope.launch {
            getPostsUseCase.execute(GetPostsUseCase.Params(logUser)).data?.map().also { posts ->
                val userPosts = mutableListOf<PostVO>()
                posts?.forEach { post ->
                    if (post.user == logUser) userPosts.add(post)
                }
                _posts.value = userPosts
            }
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
}