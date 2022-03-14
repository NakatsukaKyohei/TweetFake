package com.example.tweetfake.services

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Search {

    @GET("search/recent")
    suspend fun fetchTweets(
        @Header("Authorization") accessToken: String,
        @Query("query") searchWord: String? = null
    ): ResponseBody
}