package com.miwfem.socialtourismnetwork.data.datasource.source

import com.google.firebase.firestore.FirebaseFirestore
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao
import com.miwfem.socialtourismnetwork.utils.ResultType
import java.util.*

class FirebaseDataSource(private val firebaseFirestore: FirebaseFirestore) {

    fun savePost(post: PostDao): ResultType {
        return try {
            firebaseFirestore.collection("post").document(UUID.randomUUID().toString()).set(
                hashMapOf(
                    "user" to post.user,
                    "location" to post.location,
                    "area" to post.area,
                    "category" to post.category,
                    "comment" to post.comment
                )
            )
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

}