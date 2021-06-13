package com.spentwell.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.spentwell.data.AppDatabase
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import org.joda.time.LocalDate
import java.util.*

class ExpenseRepository(application: Application) {
    val appDatabase: AppDatabase = AppDatabase.getInstance(application)
    val expenseDao = appDatabase.expenseDao()


    fun getAllExpenses(): LiveData<List<Expense>> {
        return expenseDao.getAll()
    }

    suspend fun getAllExpensesOfType(type: ExpenseType): List<Expense> {
        return expenseDao.getAllExpensesOfType(type = type)
    }

    suspend fun getLatestExpensesOfType(
        type: ExpenseType,
        count: Int,
        limitToCurrentMonth: Boolean = false
    ): List<Expense> {
        return if (limitToCurrentMonth) {
            val today: LocalDate = LocalDate.now()
            val firstDayOfMonth = today.withDayOfMonth(1).toDate()
            val lastDayOfMonth = LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1).toDate()
            expenseDao.getAllExpensesForDateRangeAndType(
                type = type,
                startDate = firstDayOfMonth,
                endDate = lastDayOfMonth,
                count = count
            )
        } else {
            expenseDao.getLatestExpensesOfType(type = type, count = count)
        }
    }

    suspend fun getTotalExpensesOfCurrentMonth(): Double {
        val today: LocalDate = LocalDate.now()
        val firstDayOfMonth = today.withDayOfMonth(1).toDate()
        val lastDayOfMonth = LocalDate().plusMonths(1).withDayOfMonth(1).minusDays(1).toDate()
        val expensesForCurrentMonth =
            expenseDao.getAllExpensesForDateRange(
                startDate = firstDayOfMonth,
                endDate = lastDayOfMonth
            )
        var totalExpenseAmount = 0.0
        for (expense in expensesForCurrentMonth) {
            totalExpenseAmount += expense.amount
        }
        return totalExpenseAmount
    }

    suspend fun getTotalExpensesOfCurrentMonthOfType(type: ExpenseType): Double {
        val expensesForCurrentMonth = expenseDao.getAllExpensesForDateRangeAndType(
            startDate = Date(),
            endDate = Date(),
            type = type
        )
        var totalExpenseAmount = 0.0
        for (expense in expensesForCurrentMonth) {
            totalExpenseAmount += expense.amount
        }
        return totalExpenseAmount
    }

    suspend fun addNewExpense(expense: Expense) {
        expenseDao.insertAll(expense)
    }
}