package com.example.tweetfake.services

import android.util.Log
import com.example.tweetfake.BuildConfig
import com.example.tweetfake.model.FollowData
import com.example.tweetfake.model.FollowerData
import com.example.tweetfake.model.TweetData
import com.example.tweetfake.network.api_interface.FollowersFromUserID
import com.example.tweetfake.network.api_interface.FollowsFromUserID
import com.example.tweetfake.network.api_interface.TweetsFromUserID
import com.example.tweetfake.network.retrofit
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class TwitterServices {
    companion object {
        private const val TOKEN = BuildConfig.TWITTER_BEARER_TOKEN
        private const val accessToken = "Bearer $TOKEN"
        val gson = Gson()

        suspend fun getTweetsFromUserID(userID: String): TweetData {
            var result: TweetData? = null
            CoroutineScope(Dispatchers.IO).launch {
                val api = retrofit.create(TweetsFromUserID::class.java)
                val response = api.fetchTweets(accessToken = accessToken, id = userID).string()
                val tweetData: TweetData = gson.fromJson(response, TweetData::class.java)
                try {
                    result = tweetData
                } catch (e: NullPointerException) {
                    Log.d("Exception", e.toString())
                }
            }.join()

            return result!!
        }

        suspend fun getFollowersFromUserID(userID: String): FollowerData {
            var result: FollowerData? = null
            CoroutineScope(Dispatchers.IO).launch {
                val api = retrofit.create(FollowersFromUserID::class.java)
                val response = api.fetchFollowers(accessToken = accessToken, id = userID).string()
                val followerData: FollowerData = gson.fromJson(response, FollowerData::class.java)
                try {
                    result = followerData
                } catch (e: NullPointerException) {
                    Log.d("Exception", e.toString())
                }
            }.join()
            return result!!
        }

        suspend fun getFollowsFromUserID(userID: String): FollowData {
            var result: FollowData? = null
            CoroutineScope(Dispatchers.IO).launch {
                val api = retrofit.create(FollowsFromUserID::class.java)
                val response = api.fetchFollows(accessToken = accessToken, id = userID).string()
                val followData: FollowData = gson.fromJson(response, FollowData::class.java)
                try {
                    result = followData
                } catch (e: NullPointerException) {
                    Log.d("Exception", e.toString())
                }
            }.join()
            return result!!
        }
    }
}