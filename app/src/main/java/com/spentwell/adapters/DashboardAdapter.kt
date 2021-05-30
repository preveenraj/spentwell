package com.spentwell.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.spentwell.R
import com.spentwell.data.models.Expense
import com.spentwell.data.models.ExpenseType
import com.spentwell.databinding.ItemDashboardCardBinding
import com.spentwell.databinding.ItemEmptyBinding
import com.spentwell.listviewitem.DashboardViewItem
import com.spentwell.utils.AppUtils
import com.spentwell.utils.SharedPrefUtils
import kotlin.math.ceil

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
    lateinit private var binding: ItemDashboardCardBinding
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
            val item = list[position] as DashboardViewItem.ExpenseSummaryViewItem
            binding = holder.binding
            binding.apply {
                var currentSpendingInCategory: Double = 0.0
                if (item.expenseList.isNullOrEmpty()) {
                    this.tvExpenseType.text = item.title
                    this.liEmptyState.visibility = View.VISIBLE
                    this.recyclerView.visibility = View.GONE
                } else {
                    currentSpendingInCategory = item.expenseList.fold(0.0, { acc, next -> acc + next.amount })
                    this.liEmptyState.visibility = View.GONE
                    this.recyclerView.visibility = View.VISIBLE
                    this.tvExpenseType.text = item.title
                    val adapter = DashboardExpenseAdapter(item.expenseList)
                    this.recyclerView.adapter = adapter
                }

                val allocationShare = when(item.expenseType) {
                    ExpenseType.LUXURY -> SharedPrefUtils.getSharedPreferences(context)
                        .getInt(SharedPrefUtils.SHARED_PREFS_KEY_LUXURIES, 0)
                    ExpenseType.SAVINGS -> SharedPrefUtils.getSharedPreferences(context)
                        .getInt(SharedPrefUtils.SHARED_PREFS_KEY_SAVINGS, 0)
                    ExpenseType.NECESSITY -> SharedPrefUtils.getSharedPreferences(context)
                        .getInt(SharedPrefUtils.SHARED_PREF_KEY_NECESSITIES, 0)
                }
                val allocationPerMonth = incomePerMonth * allocationShare/100
                setGraphValue(currentSpendingInCategory, allocationPerMonth.toDouble());
                this.tvSeeMore.setOnClickListener { showMoreHandler(item.expenseType) }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position] is DashboardViewItem.ExpenseSummaryViewItem)
            return EXPENSE_SUMMARY_VIEW
        return super.getItemViewType(position)
    }

    private fun setGraphValue(currentSpendingInCategory: Double, allocationPerMonth: Double) {
        val balance = allocationPerMonth - currentSpendingInCategory;
        val balancePercentage = balance/allocationPerMonth
        binding.apply {
            this.allocationRemainingTextByCategory.text = "${balance.toUInt()} / ${allocationPerMonth.toUInt()}"
            val width = context.resources.getDimension(R.dimen.card_progress_width) * balancePercentage
            when ((balancePercentage * 100).toInt()) {
                in 0..30 -> cvSpendingGraph.setCardBackgroundColor(context.getColor(R.color.colorNegative))
                in 30..70 -> cvSpendingGraph.setCardBackgroundColor(context.getColor(R.color.colorNeutral))
                in 70..99 -> cvSpendingGraph.setCardBackgroundColor(context.getColor(R.color.colorPositive))
                in 100..100 -> cvSpendingGraph.setCardBackgroundColor(context.getColor(R.color.colorSuperb))
            }
            this.cvSpendingGraph.layoutParams = ConstraintLayout.LayoutParams(
                ceil(width).toInt(),
                AppUtils.convertDpToPixels(5.0f, context.resources).toInt()
            )
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class ExpenseCategoryViewHolder(val binding: ItemDashboardCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class EmptyViewHolder(val binding: ItemEmptyBinding) :
        RecyclerView.ViewHolder(binding.root)
}
