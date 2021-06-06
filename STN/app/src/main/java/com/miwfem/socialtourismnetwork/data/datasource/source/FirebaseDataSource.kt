package com.miwfem.socialtourismnetwork.data.datasource.source

import com.google.firebase.firestore.FirebaseFirestore
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseDataSource(private val firebaseFirestore: FirebaseFirestore) {

    fun savePost(post: PostDao): ResultType {
        return try {
            firebaseFirestore.collection(POST).document(UUID.randomUUID().toString()).set(
                hashMapOf(
                    USER to post.user,
                    LOCATION to post.location,
                    AREA to post.area,
                    CATEGORY to post.category,
                    COMMENT to post.comment
                )
            )
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    suspend fun getCategories(): Result<List<CategoryDao>> {
        lateinit var result: Result<List<CategoryDao>>
        val categories = mutableListOf<CategoryDao>()
        firebaseFirestore.collection(CATEGORIES).get().addOnSuccessListener { query ->
            query.documents.forEach { document ->
                if (document.id != OTHER)
                    categories.add(CategoryDao(document.id))
            }
            categories.add(categories.size, CategoryDao(OTHER))
            result = Result.success(categories)
        }.addOnFailureListener {
            result = Result.error(Exception(it))
        }.await()
        return result
    }

    companion object {
        const val POST = "post"
        const val USER = "user"
        const val LOCATION = "location"
        const val AREA = "area"
        const val CATEGORY = "category"
        const val COMMENT = "comment"
        const val CATEGORIES = "categories"
        const val OTHER = "Otro"
    }

}