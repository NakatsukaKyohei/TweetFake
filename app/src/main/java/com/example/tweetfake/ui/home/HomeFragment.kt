package com.example.tweetfake.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tweetfake.databinding.FragmentHomeBinding

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
//        val homeViewModel =
//                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val tweetOverviewList = binding.tweetOverviewList
//        homeViewModel.text.observe(viewLifecycleOwner) {
//        }

        // create dummy data
        val dummyData = Array(10) { i -> "dummy$i"}

        tweetOverviewList.let {
            val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayoutManager(this.context).orientation)
            it.addItemDecoration(dividerItemDecoration)
            it.layoutManager = LinearLayoutManager(this.context)
            it.adapter = RecyclerAdapter(dummyData)
            it.setHasFixedSize(true)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}