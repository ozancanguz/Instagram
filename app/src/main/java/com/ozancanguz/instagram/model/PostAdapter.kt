package com.ozancanguz.instagram.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozancanguz.instagram.R
import com.ozancanguz.instagram.util.loadUrl
import kotlinx.android.synthetic.main.item_row_layout.view.*
import kotlinx.android.synthetic.main.rv.view.*

class PostAdapter(var postList:ArrayList<Post>):RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }


    var likeCount:Int=0



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.rv,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var currentPost=postList[position]
        holder.itemView.userEmailTv.text="User: "+ currentPost.email
        holder.itemView.commentTv.text=currentPost.comment

        // glide image loading
        holder.itemView.postImage.loadUrl(currentPost.downloadurl)


        holder.itemView.likeImage.setOnClickListener {
            holder.itemView.likeImage.setImageResource(R.drawable.ic_favactive)
            likeCount++
          //  holder.itemView.likeCounttv.text= likeCount.toString()
            holder.itemView.likeCOuntTV.text=likeCount.toString()
        }

    }

    override fun getItemCount(): Int {
       return postList.size
    }
}