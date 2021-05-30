package com.spentwell.listviewitem

import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType

sealed class DashboardViewItem() {
    data class ExpenseSummaryViewItem(
        val title: String,
        val expenseList: List<Expense>,
        val expenseType: ExpenseType,
    ) :
        DashboardViewItem()
}
