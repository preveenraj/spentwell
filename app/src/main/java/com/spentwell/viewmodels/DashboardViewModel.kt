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
import com.spentwell.utils.SharedPrefUtils
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

    var earnings: Float = 0.0f

    private val _expensesForCurrentMonth = MutableLiveData<Double>()
    val expensesForCurrentMonth: LiveData<Double>
        get() = _expensesForCurrentMonth

    init {
        refreshList()
        earnings = SharedPrefUtils.getSharedPreferences(application)
            .getFloat(SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS, 0.0f)

    }

    fun refreshList() {
        viewModelScope.launch {
            fetchTopExpensesOfEachCategory(4)
            fetchCurrentMonthExpenses()
        }
    }

    private fun clearAndRefreshList() {
        list.clear()
        addToList(
            DashboardViewItem.ExpenseSummaryViewItem(
                "Necessities",
                necessities,
                ExpenseType.NECESSITY
            )
        )
        addToList(DashboardViewItem.ExpenseSummaryViewItem("Savings", savings, ExpenseType.SAVINGS))
        addToList(
            DashboardViewItem.ExpenseSummaryViewItem(
                "Luxuries",
                luxuries,
                ExpenseType.LUXURY
            )
        )
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
                necessities.clear()
                luxuries.clear()
                savings.clear()
                necessities.addAll(fetchedNecessities)
                luxuries.addAll(fetchedLuxuries)
                savings.addAll(fetchedSavings)
                clearAndRefreshList()
            }

        }
    }

    private suspend fun fetchCurrentMonthExpenses() {
        withContext(Dispatchers.IO) {
            val currentMonthExpenses =
                ExpenseRepository(application = getApplication()).getTotalExpensesOfCurrentMonth()
            viewModelScope.launch {
                _expensesForCurrentMonth.value = currentMonthExpenses
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