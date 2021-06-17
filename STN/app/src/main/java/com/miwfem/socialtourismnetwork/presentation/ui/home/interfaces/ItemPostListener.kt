package com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces

import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO

interface ItemPostListener {

    fun deletePost(post: PostVO)
    fun addFavPost(post: PostVO)
    fun seeAllPost(post: PostVO)
    fun sendCommunication(post: PostVO)

}