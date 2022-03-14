package com.example.tweetfake.services

import android.util.Log
import com.example.tweetfake.BuildConfig
import com.example.tweetfake.model.TweetData
import com.example.tweetfake.network.api
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class TwitterServices {
    companion object {
        private const val TOKEN = BuildConfig.TWITTER_BEARER_TOKEN
        private const val accessToken = "Bearer $TOKEN"

        suspend fun getTweetsFromUserID(userID: String): TweetData {
            var result: TweetData? = null
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.fetchTweets(accessToken = accessToken, id = userID).string()
                val gson = Gson()
                val tweetData: TweetData = gson.fromJson(response, TweetData::class.java)
                try {
                    result = tweetData
                } catch (e: NullPointerException) {
                    Log.d("Exception", e.toString())
                }
            }.join()

            return result!!
        }
    }
}