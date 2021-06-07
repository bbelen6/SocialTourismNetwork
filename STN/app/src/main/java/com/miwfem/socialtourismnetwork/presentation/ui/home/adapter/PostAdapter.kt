package com.miwfem.socialtourismnetwork.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miwfem.socialtourismnetwork.databinding.ItemPostBinding
import com.miwfem.socialtourismnetwork.presentation.common.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces.ItemPostListener

class PostAdapter(private val posts: List<PostVO>, private val itemPostListener: ItemPostListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder)
            holder.bindData(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(post: PostVO) {
            with(binding) {
                location.text = post.location
                category.text = post.category
                comment.text = post.comment
                user.text = post.user

                seeAllPost.setOnClickListener {
                    itemPostListener.seeAllPost(post)
                }
                deletePost.setOnClickListener {
                    itemPostListener.deletePost(post)
                }
                favoritePost.setOnClickListener {
                    itemPostListener.addFavPost(post)
                }
            }
        }
    }

}