package com.example.tweetfake.model

import java.util.*

data class Tweet(val authorId: String, val id: String, val text: String, val created_at: Date, val entities: Entity?)

