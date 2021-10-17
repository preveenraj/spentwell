package com.spentwell.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import java.util.*

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAll(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE type=:type ORDER BY dateTime LIMIT :count")
    fun getLatestExpensesOfType(type: ExpenseType, count: Int): List<Expense>

    @Query("SELECT * FROM expense WHERE type=:type ORDER BY dateTime")
    fun getAllExpensesOfType(type: ExpenseType): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Expense): List<Long>

    @Query("SELECT * FROM expense WHERE dateTime>:startDate AND dateTime<:endDate  ORDER BY dateTime")
    fun getAllExpensesForDateRange(startDate: Date, endDate: Date): List<Expense>

    @Query("SELECT * FROM expense WHERE dateTime>:startDate AND dateTime<:endDate AND type=:type ORDER BY dateTime")
    fun getAllExpensesForDateRangeAndType(
        startDate: Date,
        endDate: Date,
        type: ExpenseType
    ): List<Expense>

    @Query("SELECT * FROM expense WHERE dateTime>:startDate AND dateTime<:endDate AND type=:type ORDER BY dateTime LIMIT :count")
    fun getAllExpensesForDateRangeAndType(
        startDate: Date,
        endDate: Date,
        type: ExpenseType,
        count: Int
    ): List<Expense>

    @Delete
    fun delete(user: Expense)

}