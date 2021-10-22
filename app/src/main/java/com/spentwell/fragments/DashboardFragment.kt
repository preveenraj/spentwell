package com.spentwell.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.adapters.DashboardAdapter
import com.spentwell.data.models.ExpenseType
import com.spentwell.databinding.FragmentDashboardBinding
import com.spentwell.utils.AppUtils
import com.spentwell.utils.AppUtils.convertDpToPixels
import com.spentwell.viewmodels.DashboardViewModel
import kotlin.math.ceil

class DashboardFragment : Fragment() {
    private val TAG = "SpentWell"

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardBinding
    private var adapter: DashboardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard, container, false
        )
        return binding.root
    }

    private fun setViews() {
        binding.fabAddExpense.setOnClickListener {
            val navDirections =
                DashboardFragmentDirections.actionDashboardFragmentToExpenseEntryFragment()
            findNavController().navigate(navDirections)
        }
        binding.settingsButton.setOnClickListener {
            val navDirections =
                DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment()
            findNavController().navigate(navDirections)
        }

        setAdapter()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }

    private fun setAdapter() {
        val showMoreExpensesHandler = fun(expenseType: ExpenseType) {
            val navDirections =
                DashboardFragmentDirections.actionDashboardFragmentToCategoryExpensesFragment(
                    expenseType
                )
            findNavController().navigate(navDirections)
        }

        adapter = DashboardAdapter(viewModel.list, showMoreExpensesHandler)
        binding.recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        setViews()
        setObservers()
    }

    private fun setObservers() {
        viewModel.refreshList.observe(viewLifecycleOwner, {
            if (it) {
                adapter?.notifyDataSetChanged()
                viewModel.onListRefreshed()
            }
        })

        viewModel.expensesForCurrentMonth.observe(viewLifecycleOwner, { expensesForCurrentMonth ->
            val balance = viewModel.earnings - expensesForCurrentMonth
            val displayText = "${AppUtils.getFormattedCurrencyString(balance)}/${
                "%.2f".format(viewModel.earnings.toDouble())
            }"
            setGraphValue(balance, viewModel.earnings.toDouble())
            binding.tvExpenseToEarnings.text = displayText
        })
    }

    private fun setGraphValue(balance: Double, earnings: Double) {
        val balancePercentage = (balance / earnings)
        val width = binding.pvSpendingGraphBackground.width * balancePercentage
       binding.apply {
           when ((balancePercentage * 100).toInt()) {
               in 0..30 -> cvSpendingGraph.setCardBackgroundColor(requireContext().getColor(R.color.colorNegative))
               in 30..70 -> cvSpendingGraph.setCardBackgroundColor(requireContext().getColor(R.color.colorNeutral))
               in 70..99 -> cvSpendingGraph.setCardBackgroundColor(requireContext().getColor(R.color.colorPositive))
               in 100..100 -> cvSpendingGraph.setCardBackgroundColor(requireContext().getColor(R.color.colorSuperb))
           }
       }
        binding.cvSpendingGraph.layoutParams = ConstraintLayout.LayoutParams(
            ceil(width).toInt(),
            convertDpToPixels(8.0f, resources).toInt()
        )
    }

}