package com.spentwell.data

import androidx.room.TypeConverter
import com.spentwell.data.models.ExpenseType

class ExpenseTypeConverter {
    @TypeConverter
    fun toExpenseType(value: String) = enumValueOf<ExpenseType>(value)

    @TypeConverter
    fun fromExpenseType(value: ExpenseType) = value.name
}