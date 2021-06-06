package com.miwfem.socialtourismnetwork.data.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.data.datasource.source.FirebaseDataSource
import com.miwfem.socialtourismnetwork.data.repository.mapper.map
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType

class FirebaseRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : IFirebaseRepository {

    override fun savePost(post: PostEntity): ResultType {
        return firebaseDataSource.savePost(post.map())
    }

    override suspend fun getPosts(): Result<List<PostEntity>> {
        firebaseDataSource.getPosts().apply {
            data?.let { posts ->
                return when (resultType) {
                    ResultType.SUCCESS -> {
                        Result.success(posts.map())
                    }
                    ResultType.ERROR -> {
                        Result.error(error)
                    }
                }
            }
        }
        return Result.error(Exception())
    }

    override suspend fun getCategories(): Result<List<CategoryEntity>> {
        firebaseDataSource.getCategories().apply {
            data?.let { categories ->
                return when (resultType) {
                    ResultType.SUCCESS -> {
                        Result.success(categories.map())
                    }
                    ResultType.ERROR -> {
                        Result.error(error)
                    }
                }
            }
        }
        return Result.error(Exception())
    }

    override fun saveCategory(category: CategoryEntity): ResultType {
        return firebaseDataSource.saveCategory(category.map())
    }
}