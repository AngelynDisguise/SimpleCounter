package com.example.simplecounter.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ViewModel scoped to MainActivity
class MyViewModel : ViewModel() {
    // LiveData
    private val _count = MutableLiveData(0)  // only ViewModel can modify this
    val count: LiveData<Int> = _count  // expose as immutable data

    fun increment() {  // logic for UI event (used in MainActivity)
        _count.value = _count.value?.plus(1)
    }

    fun reset(newValue: Int = 0) {  // logic for UI event (used in Fragment B)
        _count.value = newValue
    }
}