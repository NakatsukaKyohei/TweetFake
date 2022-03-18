package com.example.tweetfake.network.api_interface

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface TweetsFromUserID {
    @GET("users/{id}/tweets")
    suspend fun fetchTweets(
        @Path("id") id:String,
        @Header("Authorization") accessToken: String,
        @Query("user.fields") userField: String,
        @Query("expansions") expansions: String,
        @Query("max_results") max_results: Int,
        @Query("tweet.fields") tweetField: String,

        ): ResponseBody
}