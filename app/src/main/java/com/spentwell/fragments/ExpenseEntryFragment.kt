package com.spentwell.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.databinding.FragmentExpenseEntryBinding
import com.spentwell.viewmodels.ExpenseEntryViewModel

class ExpenseEntryFragment : Fragment() {

    companion object {
        fun newInstance() = ExpenseEntryFragment()
    }

    private lateinit var viewModel: ExpenseEntryViewModel
    private lateinit var binding: FragmentExpenseEntryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_expense_entry, container, false
        )
        setViews()
        return binding.root
    }

    private fun setViews() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExpenseEntryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}