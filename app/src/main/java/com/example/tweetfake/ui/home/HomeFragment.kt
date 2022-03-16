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
import com.example.tweetfake.model.Follow
import com.example.tweetfake.model.Follower
import com.example.tweetfake.model.Tweet
import com.example.tweetfake.services.TwitterServices
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.lang.NullPointerException

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

        // get tweet data
        var tweetData: MutableList<Tweet>? = null
        var followData: List<Follow>? = null
        runBlocking {

            async {
                try {
                    followData = TwitterServices.getFollowsFromUserID("786544808705691652").data
//                    Log.d("info", followData.toString())
                } catch(e: NullPointerException) {
                    Log.d("Exception", e.toString())
                }
            }.join()
        }

        runBlocking {
            try {
//                followData!!.map { i -> tweetData?.plus(TwitterServices.getTweetsFromUserID(i.id).data)}
                tweetData = TwitterServices.getTweetsFromUserID(followData!![0].id).data.toMutableList()
                tweetData!!.addAll(TwitterServices.getTweetsFromUserID(followData!![1].id).data)
//                tweetData!!.plus(TwitterServices.getTweetsFromUserID(followData!![1].id).data)
//                followData!!.map {
//                    tweetData?.plus(TwitterServices.getTweetsFromUserID(it.id).data)
//                }
                Log.d("tag_info", tweetData.toString())
                Log.d("tag_info", tweetData!!.size.toString())

            } catch(e: NullPointerException) {
                Log.d("tag_exception", e.toString())
            }
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
}