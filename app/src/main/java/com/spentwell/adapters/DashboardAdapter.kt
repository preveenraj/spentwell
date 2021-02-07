package com.spentwell.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.databinding.ItemDashboardCardBinding

class DashboardAdapter(private val list: MutableList<String>) :
    RecyclerView.Adapter<DashboardAdapter.ExpenseCategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseCategoryViewHolder {
        val binding: ItemDashboardCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_dashboard_card, parent, false
        )
        return ExpenseCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseCategoryViewHolder, position: Int) {
        val item = list[position]
        val binding = holder.binding

        binding.tvExpenseType.text = item
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ExpenseCategoryViewHolder(val binding: ItemDashboardCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}
