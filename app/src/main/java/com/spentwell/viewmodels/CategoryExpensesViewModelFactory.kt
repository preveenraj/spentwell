package com.spentwell.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spentwell.data.models.ExpenseType


class CategoryExpensesViewModelFactory(
    private val application: Application,
    private val expenseType: ExpenseType
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryExpensesViewModel(application, expenseType) as T
    }
}