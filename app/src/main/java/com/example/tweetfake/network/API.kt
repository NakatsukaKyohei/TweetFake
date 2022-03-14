package com.example.tweetfake.network

import com.example.tweetfake.network.api_interface.TweetsFromUserID
import retrofit2.Retrofit

private val BASE_URL = "https://api.twitter.com/2/"

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

val api = retrofit.create(TweetsFromUserID::class.java)


