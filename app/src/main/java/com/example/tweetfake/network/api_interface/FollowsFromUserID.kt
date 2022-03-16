package com.example.tweetfake.network.api_interface

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FollowsFromUserID {
    @GET("users/{id}/following")
    suspend fun fetchFollows(
        @Path("id") id:String,
        @Header("Authorization") accessToken: String,
    ): ResponseBody
}