package com.spentwell.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.data.models.Expense
import com.spentwell.databinding.ItemDashboardExpenseItemBinding
import com.spentwell.utils.AppUtils

class DashboardExpenseAdapter(private val list: List<Expense>) :
    RecyclerView.Adapter<DashboardExpenseAdapter.ExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding: ItemDashboardExpenseItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_dashboard_expense_item, parent, false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = list[position]
        val binding = holder.binding
        binding.expenseTitle.text = expense.name
        binding.expenseAmount.text = AppUtils.getFormattedCurrencyString(expense.amount)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ExpenseViewHolder(val binding: ItemDashboardExpenseItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}