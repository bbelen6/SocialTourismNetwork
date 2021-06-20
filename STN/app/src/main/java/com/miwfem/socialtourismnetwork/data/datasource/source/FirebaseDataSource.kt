package com.miwfem.socialtourismnetwork.data.datasource.source

import com.google.firebase.firestore.FirebaseFirestore
import com.miwfem.socialtourismnetwork.data.datasource.model.CategoryDao
import com.miwfem.socialtourismnetwork.data.datasource.model.MessageDao
import com.miwfem.socialtourismnetwork.data.datasource.model.PostDao
import com.miwfem.socialtourismnetwork.data.datasource.model.UserDao
import com.miwfem.socialtourismnetwork.utils.OTHER
import com.miwfem.socialtourismnetwork.utils.Result
import com.miwfem.socialtourismnetwork.utils.ResultType
import com.miwfem.socialtourismnetwork.utils.USER_NAME
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.HashMap

class FirebaseDataSource(private val firebaseFirestore: FirebaseFirestore) {

    fun saveUserProfile(user: UserDao): ResultType {
        return try {
            val dummyMap = HashMap<String, String>()
            val doc = firebaseFirestore.collection(USER_SETTINGS).document(user.email)
            doc.set(dummyMap)
            doc.collection(PROFILE).document(USER_NAME).set(
                hashMapOf(
                    USER_NAME to user.name
                )
            )
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    suspend fun getUserNameByEmail(email: String): Result<String> {
        lateinit var result: Result<String>
        firebaseFirestore.collection(USER_SETTINGS).document(email).collection(PROFILE).get()
            .addOnSuccessListener {
                result = if (it.documents.isNotEmpty()) {
                    Result.success(it.documents[0].data?.get(USER_NAME).toString())
                } else {
                    Result.success("")
                }
            }.addOnFailureListener {
                result = Result.error(java.lang.Exception(it))
            }.await()
        return result
    }

    fun savePost(post: PostDao): ResultType {
        return try {
            firebaseFirestore.collection(POST)
                .document(if (post.id.isNullOrEmpty()) UUID.randomUUID().toString() else post.id)
                .set(
                    createPost(post)
                )
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    suspend fun getPosts(logUser: String?): Result<List<PostDao>> {
        lateinit var result: Result<List<PostDao>>
        val posts = mutableListOf<PostDao>()
        val favPosts = mutableListOf<String>()
        logUser?.let {
            firebaseFirestore.collection(USER_SETTINGS).document(it).collection(POSTS_FAV).get()
                .addOnSuccessListener { postsFav ->
                    postsFav.documents.forEach { post ->
                        favPosts.add(post.id)
                    }
                }.await()
        }

        firebaseFirestore.collection(POST).get().addOnSuccessListener {
            it.documents.forEach { post ->
                post.data?.apply {
                    posts.add(
                        PostDao(
                            post.id,
                            get(USER).toString(),
                            if (get(USER_NAME) == null) "" else get(USER_NAME).toString(),
                            get(LOCATION).toString(),
                            get(AREA).toString(),
                            get(CATEGORY).toString(),
                            get(COMMENT).toString(),
                            favPosts.contains(post.id),
                            get(WITH_FAV) as? Long ?: 0
                        )
                    )
                }
            }
            result = Result.success(posts)
        }.addOnFailureListener {
            result = Result.error(Exception(it))
        }.await()
        return result
    }

    fun deletePost(post: PostDao): ResultType {
        return try {
            post.id?.let { postId ->
                firebaseFirestore.collection(POST).document(postId).delete()
                if (post.withFav != 0L) {
                    firebaseFirestore.collection(USER_SETTINGS).get()
                        .addOnSuccessListener { result ->
                            result.documents.forEach { doc ->
                                firebaseFirestore.collection(USER_SETTINGS)
                                    .document(doc.reference.id).collection(POSTS_FAV)
                                    .document(postId).delete()
                            }
                        }
                }
            }
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    fun addFavoritePost(post: PostDao, logUser: String?): ResultType {
        return try {
            post.id?.let {
                firebaseFirestore.collection(POST).document(it).set(
                    createPost(post)
                )
                logUser?.let { logUser ->
                    val dummyMap = HashMap<String, String>()
                    val doc = firebaseFirestore.collection(USER_SETTINGS).document(logUser)
                    doc.set(dummyMap)
                    doc.collection(POSTS_FAV).document(post.id).set(
                        createPost(post)
                    )
                }

            }
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    private fun createPost(post: PostDao): HashMap<*, *> {
        return hashMapOf(
            USER to post.user,
            LOCATION to post.location,
            AREA to post.area,
            CATEGORY to post.category,
            COMMENT to post.comment,
            WITH_FAV to post.withFav
        )
    }

    fun deleteFavoritePost(post: PostDao, logUser: String?): ResultType {
        return try {
            post.id?.let {
                firebaseFirestore.collection(POST).document(it).set(
                    hashMapOf(
                        USER to post.user,
                        LOCATION to post.location,
                        AREA to post.area,
                        CATEGORY to post.category,
                        COMMENT to post.comment,
                        WITH_FAV to post.withFav
                    )
                )
                logUser?.let { logUser ->
                    firebaseFirestore.collection(USER_SETTINGS).document(logUser)
                        .collection(POSTS_FAV).document(post.id).delete()
                }
            }
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

    fun sendMessage(message: MessageDao): ResultType {
        return try {
            val dummyMap = HashMap<String, String>()
            val doc = firebaseFirestore.collection(USER_SETTINGS).document(message.userReceptor)
            doc.set(dummyMap)
            doc.collection(MESSAGES).document(message.postId).set(
                hashMapOf(
                    MESSAGE to message.message,
                    USER_EMISSARY_MAIL to (message.userEmissaryEmail ?: ""),
                    USER_EMISSARY_NAME to message.userEmissary,
                    POST to message.post
                )
            )
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    suspend fun getMessages(logUser: String?): Result<List<MessageDao>> {
        lateinit var result: Result<List<MessageDao>>
        val messages = mutableListOf<MessageDao>()
        logUser?.let { logUser ->
            firebaseFirestore.collection(USER_SETTINGS).document(logUser).collection(MESSAGES).get()
                .addOnSuccessListener { allMessages ->
                    allMessages.documents.forEach { messageId ->
                        messages.add(
                            MessageDao(
                                userEmissary = messageId.data?.get(USER_EMISSARY_NAME).toString(),
                                userEmissaryEmail = messageId.data?.get(USER_EMISSARY_MAIL)
                                    .toString(),
                                postId = messageId.id,
                                userReceptor = logUser,
                                message = messageId.data?.get(MESSAGE).toString(),
                                post = messageId.data?.get(POST).toString(),
                            )
                        )
                    }
                    result = Result.success(messages)
                }.addOnFailureListener {
                    result = Result.error(Exception(it))
                }.await()
        }
        return result
    }

    fun deleteMessage(message: MessageDao): ResultType {
        return try {
            firebaseFirestore.collection(USER_SETTINGS).document(message.userReceptor)
                .collection(MESSAGES).document(message.postId).delete()
            ResultType.SUCCESS
        } catch (exception: Exception) {
            ResultType.ERROR
        }
    }

    companion object {
        const val POST = "post"
        const val POSTS_FAV = "postsFav"
        const val USER = "user"
        const val LOCATION = "location"
        const val AREA = "area"
        const val CATEGORY = "category"
        const val COMMENT = "comment"
        const val CATEGORIES = "categories"
        const val WITH_FAV = "withFav"
        const val USER_SETTINGS = "userSettings"
        const val PROFILE = "profile"
        const val USERS = "users"
        const val MESSAGES = "messages"
        const val POST_ID = "postID"
        const val MESSAGE = "message"
        const val USER_EMISSARY_MAIL = "userEmisorMail"
        const val USER_EMISSARY_NAME = "userEmisorName"
    }

}