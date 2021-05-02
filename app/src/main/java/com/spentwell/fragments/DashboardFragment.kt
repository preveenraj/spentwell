package com.spentwell.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.adapters.DashboardAdapter
import com.spentwell.data.models.ExpenseType
import com.spentwell.databinding.FragmentDashboardBinding
import com.spentwell.viewmodels.DashboardViewModel

class DashboardFragment : Fragment() {

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
    }

}