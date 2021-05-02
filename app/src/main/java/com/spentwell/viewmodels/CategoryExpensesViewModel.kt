package com.spentwell.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import com.spentwell.data.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryExpensesViewModel(application: Application, val expenseType: ExpenseType) :
    AndroidViewModel(application) {
    val list = mutableListOf<Expense>()

    private val _refreshList = MutableLiveData<Boolean>()
    val refreshList: LiveData<Boolean>
        get() = _refreshList

    init {
        refreshList()
    }

    fun refreshList() {
        viewModelScope.launch {
            fetchExpensesOfCategory()
        }
    }

    private fun clearAndRepopulateList(expenses: List<Expense>) {
        list.clear()
        list.addAll(expenses)
        _refreshList.value = true
    }

    private suspend fun fetchExpensesOfCategory() {
        withContext(Dispatchers.IO) {
            val fetchedExpenses =
                ExpenseRepository(application = getApplication()).getAllExpensesOfType(
                    expenseType
                )
            viewModelScope.launch {
                clearAndRepopulateList(fetchedExpenses)
            }

        }
    }

    fun onListRefreshed() {
        _refreshList.value = false
    }
}