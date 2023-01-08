package com.ozancanguz.instagram.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozancanguz.instagram.R
import kotlinx.android.synthetic.main.item_row_layout.view.*

class PostAdapter(var postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }


    var likeCount:Int=0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item_row_layout,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var currentPost=postList[position]
        holder.itemView.userEmailTv.text=currentPost.email
        holder.itemView.userComment.text=currentPost.comment

        // glide image loading


        holder.itemView.likeImage.setOnClickListener {
            holder.itemView.likeImage.setImageResource(R.drawable.favactive)
            likeCount++
            holder.itemView.likeCounttv.text= likeCount.toString()

        }

    }

    override fun getItemCount(): Int {
       return postList.size
    }
}