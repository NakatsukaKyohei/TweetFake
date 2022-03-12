package com.example.tweetfake.ui.dm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DirectMessageViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is direct message Fragment"
    }
    val text: LiveData<String> = _text
}