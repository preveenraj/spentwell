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

    suspend fun getAllExpensesOfType(type: ExpenseType): List<Expense> {
        return expenseDao.getAllExpensesOfType(type = type)
    }

    suspend fun getLatestExpensesOfType(type: ExpenseType, count: Int): List<Expense> {
        return expenseDao.getLatestExpensesOfType(type = type, count = count)
    }

    suspend fun addNewExpense(expense: Expense) {
        expenseDao.insertAll(expense)
    }
}