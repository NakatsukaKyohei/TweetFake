package com.example.tweetfake.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tweetfake.BuildConfig
import com.example.tweetfake.databinding.FragmentHomeBinding
import com.example.tweetfake.model.Tweet
import com.example.tweetfake.model.TweetData
import com.example.tweetfake.network.api
import com.google.gson.Gson
import kotlinx.coroutines.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val tweetOverviewList = binding.tweetOverviewList

        // create dummy data
        var tweetData: List<Tweet>?
        runBlocking {
            tweetData = searchTweet().data
        }
        val tweetContents = Array(tweetData!!.size) { i -> tweetData!![i].text}
        val tweetIDs = Array(tweetData!!.size) { i -> tweetData!![i].id}

        tweetOverviewList.let {
            val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayoutManager(this.context).orientation)
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = RecyclerAdapter(tweetIDs, tweetContents)
            it.setHasFixedSize(true)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun searchTweet(): TweetData {
        val id = "786544808705691652"
        var result: TweetData? = null
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.fetchTweets(accessToken = "Bearer ${BuildConfig.TWITTER_BEARER_TOKEN}", id = id).string()
            val gson = Gson()
            val tweetData: TweetData = gson.fromJson(response, TweetData::class.java)
            result = tweetData
        }.join()

        return result!!
    }

}