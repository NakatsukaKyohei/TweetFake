package com.example.tweetfake.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tweetfake.databinding.FragmentHomeBinding
import com.example.tweetfake.model.Tweet
import com.example.tweetfake.model.TweetData
import com.example.tweetfake.network.api
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.reflect.Type


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
        var dummyContent: List<Tweet>? = null
        runBlocking {
            dummyContent = searchTweet().data
        }
        val dummyTweet = Array(dummyContent!!.size) { i -> dummyContent!![i].text}
        val dummyData = Array(dummyContent!!.size) { i -> "dummy${dummyContent!![i].id}"}

//        val dummyContent = Array(1) { i -> await searchTweet()}
        tweetOverviewList.let {
            val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayoutManager(this.context).orientation)
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = RecyclerAdapter(dummyData, dummyTweet)
            it.setHasFixedSize(true)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    suspend fun searchTweet(): TweetData {
        val BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAALrn9QAAAAAADPNpDAGVGUn2BzlqCEqezBzbwiw%3Dc8qGv2ep456mXIztO9nCnBawzT47gl1sRPh2LZ79880xvh5T1G"
        val query = "sample"
        var result: TweetData? = null
        CoroutineScope(Dispatchers.IO).launch {

            val response = api.fetchTweets(accessToken = "Bearer $BEARER_TOKEN", searchWord = query).string()
            Log.d("info", response)
            val gson = Gson()
            val tweetData: TweetData = gson.fromJson(response, TweetData::class.java)
//            val listType: Type = object : TypeToken<List<Tweet>?>() {}.type
//            val tweetInfo: List<Tweet> = gson.fromJson(response, listType)
//            withContext(Dispatchers.Main) {
////                textView.text = response
//            }
            result = tweetData

            Log.d("GSON", tweetData.toString())
        }.join()

        return result!!
    }

}