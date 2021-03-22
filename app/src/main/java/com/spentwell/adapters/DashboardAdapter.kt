package com.spentwell.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.databinding.ItemDashboardCardBinding
import com.spentwell.databinding.ItemEmptyBinding
import com.spentwell.listviewitem.DashboardViewItem

class DashboardAdapter(private val list: MutableList<DashboardViewItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val EXPENSE_SUMMARY_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == EXPENSE_SUMMARY_VIEW) {
            val binding: ItemDashboardCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_dashboard_card, parent, false
            )
            ExpenseCategoryViewHolder(binding)
        } else {
            val binding: ItemEmptyBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_empty, parent, false
            )
            EmptyViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExpenseCategoryViewHolder) {
            val item = list[position] as DashboardViewItem.ExpenseSummaryViewItem
            val binding = holder.binding
            if (item.expenseList.isNullOrEmpty()) {
                binding.tvExpenseType.text = item.title
                binding.liEmptyState.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.liEmptyState.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvExpenseType.text = item.title
                val adapter = DashboardExpenseAdapter(item.expenseList)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position] is DashboardViewItem.ExpenseSummaryViewItem)
            return EXPENSE_SUMMARY_VIEW
        return super.getItemViewType(position)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class ExpenseCategoryViewHolder(val binding: ItemDashboardCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class EmptyViewHolder(val binding: ItemEmptyBinding) :
        RecyclerView.ViewHolder(binding.root)
}
