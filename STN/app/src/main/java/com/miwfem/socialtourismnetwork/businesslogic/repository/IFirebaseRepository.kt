package com.miwfem.socialtourismnetwork.businesslogic.repository

import com.miwfem.socialtourismnetwork.businesslogic.model.PostEntity
import com.miwfem.socialtourismnetwork.utils.ResultType

interface IFirebaseRepository {

    fun savePost(post: PostEntity): ResultType

}