package com.example.tweetfake.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tweetfake.R
import com.example.tweetfake.model.CustomTweet

class RecyclerAdapter(val tweetContentList: MutableList<CustomTweet>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(){

    class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tweetUserImage: ImageView = view.findViewById(R.id.tweet_user_image)
        val tweetUsername: TextView = view.findViewById(R.id.tweet_user_name)
        val tweetContent: TextView = view.findViewById(R.id.tweet_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.view_tweet, parent, false)
        return RecyclerViewHolder(item)
    }

    override fun getItemCount(): Int = tweetContentList.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let{
            it.tweetUserImage.setImageResource(R.mipmap.ic_launcher_round)
            it.tweetUsername.text = tweetContentList[position].name
            it.tweetContent.text = tweetContentList[position].content
        }
    }
}
