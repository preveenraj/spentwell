package com.spentwell.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.spentwell.data.AppDatabase
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType

class ExpenseRepository(application: Application) {
    val appDatabase: AppDatabase = AppDatabase.getInstance(application)
    val expenseDao = appDatabase.expenseDao()


    fun getAllExpenses(): LiveData<List<Expense>> {
        return expenseDao.getAll()
    }

    fun getLatestExpensesOfType(type: ExpenseType, count: Int = 5): LiveData<List<Expense>> {
        return expenseDao.getLatestExpensesOfType(type = type, count = count)
    }

    fun addNewExpense(expense: Expense) {
        expenseDao.insertAll(expense)
    }
}