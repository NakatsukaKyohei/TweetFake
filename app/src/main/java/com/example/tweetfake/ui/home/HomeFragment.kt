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
import com.example.tweetfake.model.CustomTweet
import com.example.tweetfake.model.Follow
import com.example.tweetfake.model.TweetData
import com.example.tweetfake.services.TwitterServices
import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @OptIn(ExperimentalTime::class)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val tweetOverviewList = binding.tweetOverviewList

        // get tweet data
        val tweetDataList: MutableList<TweetData> = mutableListOf()

        val tweetContentList: MutableList<CustomTweet> = mutableListOf()
        var followData: List<Follow> = listOf()
        val time1 = measureTime {
            runBlocking {
                followData = TwitterServices.getFollowsFromUserID("786544808705691652").data
                    try {
                        followData.map {
                            async {
                                tweetDataList.add(TwitterServices.getTweetsFromUserID(it.id))
                            }
                        }.awaitAll()
                        Log.d("info", followData.toString())
                    } catch(e: NullPointerException) {
                        Log.d("Exception", e.toString())
                    }
            }
        }


        val time2 = measureTime {
            runBlocking {
                followData.mapIndexed { index, follow ->
//                async {
                    tweetDataList[index].data.map {
                        async {
                            tweetContentList.add(
                                CustomTweet(
                                    name = follow.name,
                                    content = it.text
                                )
                            )
                        }
                    }.awaitAll()
//                }
                }
            }
        }

        Log.d("time1", time1.toString())
        Log.d("time2", time2.toString())

        tweetOverviewList.let {
            val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayoutManager(this.context).orientation)
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = RecyclerAdapter(tweetContentList)
            it.setHasFixedSize(true)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}