package com.spentwell.data

import androidx.room.TypeConverter
import com.spentwell.data.models.ExpenseType
import java.util.*

class ExpenseTypeConverter {
    @TypeConverter
    fun toExpenseType(value: String) = enumValueOf<ExpenseType>(value)

    @TypeConverter
    fun fromExpenseType(value: ExpenseType) = value.name
}

class DateConverter {
    @TypeConverter
    fun toDate(value: Long) = Date(value)

    @TypeConverter
    fun fromDate(value: Date) = value.time
}