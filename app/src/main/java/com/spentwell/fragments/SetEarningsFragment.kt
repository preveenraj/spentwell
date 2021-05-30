package com.spentwell.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.databinding.FragmentSetEarningsBinding
import com.spentwell.utils.SharedPrefUtils
import com.spentwell.viewmodels.SetEarningsViewModel
import kotlinx.android.synthetic.main.fragment_set_earnings.view.*


class SetEarningsFragment : Fragment() {

    companion object {
        fun newInstance() = SetEarningsFragment()
    }
    private lateinit var viewModel: SetEarningsViewModel
    private lateinit var binding: FragmentSetEarningsBinding
    private lateinit var etEarnings: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_set_earnings, container, false
        )
        binding.lifecycleOwner = this
        etEarnings = binding.earningsContainer.incomePerMonth

        setViews()
        return binding.root
    }

    private fun setViews() {
        etEarnings.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    saveAndProceed()
                    true
                }
                else -> false
            }
        }
        binding.btProceed.setOnClickListener{
          saveAndProceed()
        }
    }

    private fun saveAndProceed() {
        if (etEarnings.text.isNotEmpty() && etEarnings.text.toString().toFloat() > 1000.0f) {
            SharedPrefUtils.getSharedPreferences(requireContext()).edit().putFloat(
                SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS,
                etEarnings.text.toString().toFloat()
            ).apply()
            hideKeyboard()
            val navDirections =
                SetEarningsFragmentDirections.actionSetEarningsFragmentToDashboardFragment()
            findNavController().navigate(navDirections)
        } else {
            Toast.makeText(
                requireContext(),
                "Please enter a valid income per month",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun hideKeyboard() {
        // Hide the keyboard.
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetEarningsViewModel::class.java)
        binding.model = viewModel
        // TODO: Use the ViewModel
    }

}