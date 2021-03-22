package com.spentwell.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.data.models.Expense
import com.spentwell.databinding.ItemDashboardExpenseItemBinding
import java.text.NumberFormat
import java.util.*

class DashboardExpenseAdapter(private val list: List<Expense>) :
    RecyclerView.Adapter<DashboardExpenseAdapter.ExpenseExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseExpenseViewHolder {
        val binding: ItemDashboardExpenseItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_dashboard_expense_item, parent, false
        )
        return ExpenseExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseExpenseViewHolder, position: Int) {
        val expense = list[position]
        val binding = holder.binding
        binding.expenseTitle.text = expense.name
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("INR")
        binding.expenseAmount.text = format.format(expense.amount)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ExpenseExpenseViewHolder(val binding: ItemDashboardExpenseItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}