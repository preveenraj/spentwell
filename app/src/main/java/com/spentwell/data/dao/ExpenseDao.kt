package com.spentwell.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getAll(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE type=:type ORDER BY dateTime LIMIT :count")
    fun getLatestExpensesOfType(type: ExpenseType, count: Int = 5): LiveData<List<Expense>>

    @Insert
    fun insertAll(vararg users: Expense)

    @Delete
    fun delete(user: Expense)

}