package com.spentwell.fragments

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
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

    override fun onStop() {
        binding.root.let { view ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
        super.onStop()
    }

    private fun setViews() {
        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.etAmount.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onSubmitExpense()
            }
            false
        }
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