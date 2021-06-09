package com.miwfem.socialtourismnetwork.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.ItemPostBinding
import com.miwfem.socialtourismnetwork.presentation.common.interfaces.AddDataSetAdapterLister
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.home.interfaces.ItemPostListener

class PostAdapter(
    private val itemPostListener: ItemPostListener,
    private val logUser: String?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), AddDataSetAdapterLister<PostVO> {

    private val dataSet = mutableListOf<PostVO>()

    override fun addDataSet(items: List<PostVO>) {
        dataSet.clear()
        if (items.isNotEmpty()) dataSet.addAll(items)
        notifyDataSetChanged()
    }

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
            holder.bindData(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(post: PostVO) {
            with(binding) {
                location.text = post.location
                category.text = post.category
                comment.text = post.comment
                user.text = post.user
                numberFavs.text = post.withFav.toString()

                deletePost.isVisible = post.user == logUser
                favoritePost.isVisible = logUser != null
                numberFavs.isVisible = logUser != null

                if (post.isFav) {
                    favoritePost.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    favoritePost.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }

                seeAllPost.setOnClickListener {
                    itemPostListener.seeAllPost(post)
                }
                deletePost.setOnClickListener {
                    itemPostListener.deletePost(post)
                }
                favoritePost.setOnClickListener {
                    post.isFav = !post.isFav
                    if (post.isFav) {
                        post.withFav += 1
                        favoritePost.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else if (post.withFav > 0) {
                        post.withFav -= 1
                        favoritePost.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                    numberFavs.isVisible = post.withFav != 0L
                    numberFavs.text = post.withFav.toString()
                    itemPostListener.addFavPost(post)
                }
            }
        }
    }

}