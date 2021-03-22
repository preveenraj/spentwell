package com.spentwell.listviewitem

import com.spentwell.data.models.Expense

sealed class DashboardViewItem() {
    data class ExpenseSummaryViewItem(val title: String, val expenseList: List<Expense>) :
        DashboardViewItem()
}
