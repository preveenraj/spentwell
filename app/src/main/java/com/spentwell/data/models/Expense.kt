package com.spentwell.data.models

import androidx.room.Entity

@Entity
data class Expense(
    val name: String,
    val type: ExpenseType,
    val amount: Double
)