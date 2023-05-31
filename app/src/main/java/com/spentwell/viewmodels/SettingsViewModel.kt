package com.spentwell.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {


    private val _isSaveButtonEnabled = MutableLiveData<Boolean>()
    val isSaveButtonEnabled: LiveData<Boolean>
        get() = _isSaveButtonEnabled

    init {
        _isSaveButtonEnabled.value = true
    }

    fun toggleSaveButton(b: Boolean) {
        _isSaveButtonEnabled.value = b
    }
}