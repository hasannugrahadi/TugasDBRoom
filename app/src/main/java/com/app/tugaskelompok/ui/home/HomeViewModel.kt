package com.app.tugaskelompok.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel  : ViewModel(){
    private val _texts = MutableLiveData<List<String>>().apply {
        value = (1..10).mapIndexed { _, i ->
            "Orang $i"
        }
    }

    val texts: LiveData<List<String>> = _texts
}