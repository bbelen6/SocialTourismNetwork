package com.miwfem.socialtourismnetwork.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.ItemPostBinding
import com.miwfem.socialtourismnetwork.presentation.common.PostVO

class PostAdapter(private val posts: List<PostVO>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var postBinding: ItemPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        postBinding = ItemPostBinding.bind(view)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postHolder = holder as PostViewHolder
        postHolder.bindData(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(post: PostVO) {
            with(postBinding) {
                location.text = post.location
                category.text = post.category
                comment.text = post.comment
                user.text = post.user
            }
        }
    }

}