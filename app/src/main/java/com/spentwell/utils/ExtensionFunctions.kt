package com.spentwell.utils

import com.spentwell.data.models.ExpenseType

fun Collection<*>?.isNotNullOrEmpty(): Boolean {
    return !(this.isNullOrEmpty())
}

fun String?.isNotNullOrEmpty(): Boolean {
    return !(this.isNullOrEmpty())
}

fun ExpenseType.toPluralString(): String {
    return when (this) {
        ExpenseType.LUXURY -> {
            "Luxuries"
        }
        ExpenseType.SAVINGS -> {
            "Savings"

        }
        ExpenseType.NECESSITY -> {
            "Necessities"

        }
    }
}