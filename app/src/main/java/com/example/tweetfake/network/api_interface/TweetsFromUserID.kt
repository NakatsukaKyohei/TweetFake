package com.example.tweetfake.network.api_interface

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TweetsFromUserID {
    @GET("users/{id}/tweets")
    suspend fun fetchTweets(
        @Path("id") id:String,
        @Header("Authorization") accessToken: String,
    ): ResponseBody
}