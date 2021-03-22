package com.spentwell.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.data.models.ExpenseType
import com.spentwell.databinding.FragmentExpenseEntryBinding
import com.spentwell.viewmodels.ExpenseEntryViewModel


class ExpenseEntryFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = ExpenseEntryFragment()
        val expenseTypes = arrayOf("Necessity", "Savings", "Luxury")
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
        binding.lifecycleOwner = this

        setViews()
        return binding.root
    }

    private fun setObservers() {
        viewModel.eventCloseNavigation.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
                viewModel.onCloseNavigationCompleted()
            }
        })
    }

    private fun setViews() {
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, expenseTypes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spExpenseType.onItemSelectedListener = this
        binding.spExpenseType.adapter = spinnerAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExpenseEntryViewModel::class.java)
        binding.model = viewModel
        setObservers()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> viewModel.setExpenseType(ExpenseType.NECESSITY)
            1 -> viewModel.setExpenseType(ExpenseType.SAVINGS)
            2 -> viewModel.setExpenseType(ExpenseType.LUXURY)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}