package com.miwfem.socialtourismnetwork.data.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.CategoryEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.MessageEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.data.datasource.room.model.CategoryDaoLocal
import com.miwfem.socialtourismnetwork.data.datasource.room.model.CategoryEntityLocal
import com.miwfem.socialtourismnetwork.data.datasource.source.FirebaseDataSource
import com.miwfem.socialtourismnetwork.data.repository.mapper.map
import com.miwfem.socialtourismnetwork.data.repository.mapper.mapLocal
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType

class FirebaseRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val categoryDaoLocal: CategoryDaoLocal
) : IFirebaseRepository {

    override fun saveUserProfile(user: UserEntity): ResultType {
        return firebaseDataSource.saveUserProfile(user.map())
    }

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
        val localCategories = getLocalCategories()
        if (localCategories.isNullOrEmpty()) {
            firebaseDataSource.getCategories().apply {
                data?.let { categories ->
                    return when (resultType) {
                        ResultType.SUCCESS -> {
                            insertLocalCategories(categories.mapLocal())
                            Result.success(categories.map())
                        }
                        ResultType.ERROR -> {
                            Result.error(error)
                        }
                    }
                }
            }
        } else {
            return Result.success(localCategories.map().map())
        }
        return Result.error(Exception())
    }

    override suspend fun getUserNameByEmail(email: String): Result<String> {
        return firebaseDataSource.getUserNameByEmail(email)
    }

    override fun sendMessage(messageEntity: MessageEntity): ResultType {
        return firebaseDataSource.sendMessage(messageEntity.map())
    }

    override suspend fun getMessages(logUser: String?): Result<List<MessageEntity>> {
        firebaseDataSource.getMessages(logUser).apply {
            data?.let { messages ->
                return when (resultType) {
                    ResultType.SUCCESS -> Result.success(messages.map())
                    ResultType.ERROR -> Result.error(error)
                }
            }
        }
        return Result.error(Exception())
    }

    override fun deleteMessage(messageEntity: MessageEntity): ResultType {
        return firebaseDataSource.deleteMessage(messageEntity.map())
    }

    private suspend fun getLocalCategories(): List<CategoryEntityLocal> {
        return categoryDaoLocal.getAllCategories()
    }

    private suspend fun insertLocalCategories(categories: List<CategoryEntityLocal>) {
        categoryDaoLocal.insertCategories(categories)
    }

}