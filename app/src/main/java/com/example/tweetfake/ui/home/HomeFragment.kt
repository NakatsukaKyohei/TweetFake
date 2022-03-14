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
import com.example.tweetfake.services.TwitterServices
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
        var tweetData: List<Tweet>? = null
        runBlocking {
            try {
                tweetData = TwitterServices.getTweetsFromUserID("786544808705691652").data
            } catch(e: NullPointerException) {
                Log.d("Exception", e.toString())
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