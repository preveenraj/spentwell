package com.spentwell.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.data.models.Expense
import com.spentwell.databinding.ItemCategoryExpenseItemBinding
import com.spentwell.utils.AppUtils

class CategoryExpenseAdapter(private val list: List<Expense>) :
    RecyclerView.Adapter<CategoryExpenseAdapter.CategoryExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryExpenseViewHolder {
        val binding: ItemCategoryExpenseItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_expense_item, parent, false
        )
        return CategoryExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryExpenseViewHolder, position: Int) {
        val expense = list[position]
        val binding = holder.binding
        binding.expenseTitle.text = expense.name
        binding.expenseAmount.text = AppUtils.getFormattedCurrencyString(expense.amount)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CategoryExpenseViewHolder(val binding: ItemCategoryExpenseItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}