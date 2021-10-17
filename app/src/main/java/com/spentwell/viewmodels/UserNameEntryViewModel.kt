package com.spentwell.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class UserNameEntryViewModel : ViewModel() {

    val userName = MutableLiveData<String>()
    private val _isProceedButtonEnabled = MutableLiveData<Boolean>()
    val isProceedButtonEnabled: LiveData<Boolean>
        get() = _isProceedButtonEnabled

    private val observer = Observer { value: String ->
        Log.i("TAG", value)
        toggleProceedButton(value.isNotEmpty())
    }

    init {
        _isProceedButtonEnabled.value = false
        userName.observeForever(observer)
    }

    private fun toggleProceedButton(isButtonEnabled: Boolean) {
        Log.i("IS TOGGLE ENABLED", isButtonEnabled.toString())
        _isProceedButtonEnabled.value = isButtonEnabled
    }

    override fun onCleared() {
        userName.removeObserver(observer)
        super.onCleared()
    }
}