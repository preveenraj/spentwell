package com.spentwell.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import com.spentwell.data.repository.ExpenseRepository
import com.spentwell.listviewitem.DashboardViewItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    val list = mutableListOf<DashboardViewItem>()

    private val _refreshList = MutableLiveData<Boolean>()
    val refreshList: LiveData<Boolean>
        get() = _refreshList

    val necessities = mutableListOf<Expense>()
    val luxuries = mutableListOf<Expense>()
    val savings = mutableListOf<Expense>()

    init {
        viewModelScope.launch {
            fetchTopExpensesOfEachCategory(4)
        }
    }

    private fun clearAndRefreshList() {
        list.clear()
        addToList(DashboardViewItem.ExpenseSummaryViewItem("Necessities", necessities))
        addToList(DashboardViewItem.ExpenseSummaryViewItem("Savings", savings))
        addToList(DashboardViewItem.ExpenseSummaryViewItem("Luxuries", luxuries))
        _refreshList.value = true
    }

    private suspend fun fetchTopExpensesOfEachCategory(numberOfExpensesToFetch: Int) {
        withContext(Dispatchers.IO) {
            val fetchedNecessities =
                ExpenseRepository(application = getApplication()).getLatestExpensesOfType(
                    ExpenseType.NECESSITY,
                    numberOfExpensesToFetch
                )
            val fetchedSavings =
                ExpenseRepository(application = getApplication()).getLatestExpensesOfType(
                    ExpenseType.SAVINGS,
                    numberOfExpensesToFetch
                )
            val fetchedLuxuries =
                ExpenseRepository(application = getApplication()).getLatestExpensesOfType(
                    ExpenseType.LUXURY,
                    numberOfExpensesToFetch
                )
            viewModelScope.launch {
                necessities.addAll(fetchedNecessities)
                luxuries.addAll(fetchedLuxuries)
                savings.addAll(fetchedSavings)
                clearAndRefreshList()
            }

        }
    }

    fun onListRefreshed() {
        _refreshList.value = false
    }

    private fun addToList(item: DashboardViewItem) {
        list.add(item)
    }
}