package com.spentwell.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import com.spentwell.databinding.ItemDashboardCardBinding
import com.spentwell.databinding.ItemEmptyBinding
import com.spentwell.listviewitem.DashboardViewItem
import com.spentwell.utils.SharedPrefUtils

private const val TAG = "DashboardAdapter";

class DashboardAdapter(
    private val list: MutableList<DashboardViewItem>,
    private val showMoreHandler: (ExpenseType) -> (Unit)
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val EXPENSE_SUMMARY_VIEW = 1
    }

    lateinit private var context: Context
    private var incomePerMonth: Float = 0.0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        incomePerMonth = SharedPrefUtils.getSharedPreferences(context)
            .getFloat(SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS, 0.0f)

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
            val totalAllocationAmount = when(position) {
                0 -> SharedPrefUtils.getSharedPreferences(context)
                    .getInt(SharedPrefUtils.SHARED_PREFS_KEY_LUXURIES, 0)
                1 -> SharedPrefUtils.getSharedPreferences(context)
                    .getInt(SharedPrefUtils.SHARED_PREFS_KEY_SAVINGS, 0)
                2-> SharedPrefUtils.getSharedPreferences(context)
                    .getInt(SharedPrefUtils.SHARED_PREF_KEY_NECESSITIES, 0)
                else -> 0
            }
            val item = list[position] as DashboardViewItem.ExpenseSummaryViewItem
            val binding = holder.binding
            var currentSpendingInCategory: Double = 0.0
            if (item.expenseList.isNullOrEmpty()) {
                binding.tvExpenseType.text = item.title
                binding.liEmptyState.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                currentSpendingInCategory = item.expenseList.fold(0.0, { acc, next -> acc + next.amount })
                binding.liEmptyState.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvExpenseType.text = item.title
                val adapter = DashboardExpenseAdapter(item.expenseList)
                binding.recyclerView.adapter = adapter
            }
            binding.allocationRemainingTextByCategory.text = " ${currentSpendingInCategory} / ${(incomePerMonth.toInt()*(totalAllocationAmount.toFloat()/100)).toUInt()}"

            binding.tvSeeMore.setOnClickListener { showMoreHandler(item.expenseType) }
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
