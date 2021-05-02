package com.spentwell.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.spentwell.R
import com.spentwell.adapters.CategoryExpenseAdapter
import com.spentwell.databinding.FragmentCategoryExpensesBinding
import com.spentwell.utils.toPluralString
import com.spentwell.viewmodels.CategoryExpensesViewModel
import com.spentwell.viewmodels.CategoryExpensesViewModelFactory

class CategoryExpensesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoryExpensesFragment()
    }

    private lateinit var binding: FragmentCategoryExpensesBinding
    private lateinit var viewModel: CategoryExpensesViewModel
    private var adapter: CategoryExpenseAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category_expenses, container, false
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            CategoryExpensesViewModelFactory(
                activity?.application!!,
                CategoryExpensesFragmentArgs.fromBundle(requireArguments()).expenseType
            )
        ).get(CategoryExpensesViewModel::class.java)
        binding.model = viewModel
        setViews()
        setObservers()
    }

    private fun setViews() {
        binding.expenseType.text = viewModel.expenseType.toPluralString()
        setAdapter()
    }

    private fun setAdapter() {
        adapter = CategoryExpenseAdapter(viewModel.list)
        binding.recyclerView.adapter = adapter
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