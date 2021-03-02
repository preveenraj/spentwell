package com.spentwell.viewmodels

import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {
    val list = mutableListOf<String>()

    init {
        addToList("Necessity")
        addToList("Savings")
        addToList("Luxury")
    }

    private fun addToList(item: String) {
        list.add(item)
    }
}