package com.spentwell.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spentwell.data.models.ExpenseType

class EarningsAllocationViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val _isProceedButtonEnabled = MutableLiveData<Boolean>()
    val isProceedButtonEnabled: LiveData<Boolean>
        get() = _isProceedButtonEnabled


    init {
       _isProceedButtonEnabled.value = true
    }

    fun toggleProceedButton(b: Boolean) {
        _isProceedButtonEnabled.value = b
    }
}