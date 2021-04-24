package com.spentwell.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetEarningsViewModel : ViewModel() {

    private val _isProceedButtonEnabled = MutableLiveData<Boolean>()
    val isProceedButtonEnabled: LiveData<Boolean>
        get() = _isProceedButtonEnabled


    init {
        _isProceedButtonEnabled.value = false
    }

    fun toggleProceedButton(b: Boolean) {
        _isProceedButtonEnabled.value = b
    }
}