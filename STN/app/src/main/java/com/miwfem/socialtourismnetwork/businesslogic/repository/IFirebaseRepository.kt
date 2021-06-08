package com.miwfem.socialtourismnetwork.businesslogic.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType

interface IFirebaseRepository {

    fun savePost(post: PostEntity): ResultType
    suspend fun getPosts(logUser: String?): Result<List<PostEntity>>
    fun deletePost(post: PostEntity): ResultType
    fun addFavoritePost(post: PostEntity, logUser: String?): ResultType
    fun deleteFavoritePost(post: PostEntity, logUser: String?): ResultType
    suspend fun getCategories(): Result<List<CategoryEntity>>

}