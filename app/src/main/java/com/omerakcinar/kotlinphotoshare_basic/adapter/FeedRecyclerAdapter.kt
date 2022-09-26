package com.omerakcinar.kotlinphotoshare_basic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.omerakcinar.kotlinphotoshare_basic.R
import com.omerakcinar.kotlinphotoshare_basic.databinding.RecyclerRowBinding
import com.omerakcinar.kotlinphotoshare_basic.model.Post
import com.squareup.picasso.Picasso

class FeedRecyclerAdapter (private val postList : ArrayList<Post>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {
    class PostHolder (val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val email = postList.get(position).email
        val index = email.indexOf("@")
        val username = email.substring(0,index)
        holder.binding.userNameText.text = username
        holder.binding.commentText.text = postList.get(position).comment
        holder.binding.profilePhotoImageView.setImageResource(R.drawable.profile_icon)
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.postedImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}