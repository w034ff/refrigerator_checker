package com.example.refrigerator_checker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import android.util.Log

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()


    val texts: LiveData<Int> = Transformations.map(_index) {
        it
    }

    fun setIndex(index: Int) {
        if(index == 1){
            _index.value = 1
        } else if(index == 2){
            _index.value = 2
        } else{
            _index.value = 3
        }
    }
}