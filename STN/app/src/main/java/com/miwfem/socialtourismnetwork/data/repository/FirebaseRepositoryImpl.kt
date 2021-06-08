package com.miwfem.socialtourismnetwork.data.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.data.datasource.room.CategoryDaoLocal
import com.miwfem.socialtourismnetwork.data.datasource.room.CategoryEntityLocal
import com.miwfem.socialtourismnetwork.data.datasource.source.FirebaseDataSource
import com.miwfem.socialtourismnetwork.data.repository.mapper.map
import com.miwfem.socialtourismnetwork.data.repository.mapper.mapRoom
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType

class FirebaseRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val categoryDao: CategoryDaoLocal
) : IFirebaseRepository {

    override fun savePost(post: PostEntity): ResultType {
        return firebaseDataSource.savePost(post.map())
    }

    override suspend fun getPosts(logUser: String?): Result<List<PostEntity>> {
        firebaseDataSource.getPosts(logUser).apply {
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

    override fun deletePost(post: PostEntity): ResultType {
        return firebaseDataSource.deletePost(post.map())
    }

    override fun addFavoritePost(post: PostEntity, logUser: String?): ResultType {
        return firebaseDataSource.addFavoritePost(post.map(), logUser)
    }

    override fun deleteFavoritePost(post: PostEntity, logUser: String?): ResultType {
        return firebaseDataSource.deleteFavoritePost(post.map(), logUser)
    }

    override suspend fun getCategories(): Result<List<CategoryEntity>> {
        val roomCategories = getRoomCategories()
        if (roomCategories.isNullOrEmpty()) {
            firebaseDataSource.getCategories().apply {
                data?.let { categories ->
                    return when (resultType) {
                        ResultType.SUCCESS -> {
                            insertRoomCategories(categories.mapRoom())
                            Result.success(categories.map())
                        }
                        ResultType.ERROR -> {
                            Result.error(error)
                        }
                    }
                }
            }
        } else {
            return Result.success(roomCategories.map().map())
        }
        return Result.error(Exception())
    }

    private suspend fun getRoomCategories(): List<CategoryEntityLocal> {
        return categoryDao.getAllCategories()
    }

    private suspend fun insertRoomCategories(categories: List<CategoryEntityLocal>) {
        categoryDao.insertCategories(categories)
    }

}