package com.spentwell.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class SetEarningsViewModel : ViewModel() {

    val incomePerMonth = MutableLiveData<String>()
    private val _isProceedButtonEnabled = MutableLiveData<Boolean>()
    val isProceedButtonEnabled: LiveData<Boolean>
        get() = _isProceedButtonEnabled

    private val observer = Observer { value: String ->
        toggleProceedButton(value.isNotEmpty() && value.toInt() > 1000)
    }

    init {
        _isProceedButtonEnabled.value = false
        incomePerMonth.observeForever(observer)
    }

    private fun toggleProceedButton(isButtonEnabled: Boolean) {
        _isProceedButtonEnabled.value = isButtonEnabled
    }

    override fun onCleared() {
        incomePerMonth.removeObserver(observer)
        super.onCleared()
    }
}