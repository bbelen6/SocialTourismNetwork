package com.miwfem.socialtourismnetwork.data.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.IFirebaseRepository
import com.miwfem.socialtourismnetwork.data.datasource.source.FirebaseDataSource
import com.miwfem.socialtourismnetwork.data.repository.mapper.map
import com.miwfem.socialtourismnetwork.utils.ResultType

class FirebaseRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : IFirebaseRepository {
    override fun savePost(post: PostEntity): ResultType {
        return firebaseDataSource.savePost(post.map())
    }
}