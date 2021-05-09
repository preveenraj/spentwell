package com.spentwell.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_set_earnings, container, false
        )
        binding.lifecycleOwner = this

        setViews()
        return binding.root
    }

    private fun setViews() {
        val etEarnings = binding.earningsContainer.incomePerMonth
        binding.btProceed.setOnClickListener{
            if (etEarnings.text.isNotEmpty() && etEarnings.text.toString().toInt() > 1000) {
                SharedPrefUtils.getSharedPreferences(requireContext()).edit().putInt(
                    SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS,
                    etEarnings.text.toString().toInt()
                ).commit()
                val navDirections = SetEarningsFragmentDirections.actionSetEarningsFragmentToDashboardFragment()
                findNavController().navigate(navDirections)
            } else {
                Toast.makeText(requireContext(),"Please enter a valid income per month",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetEarningsViewModel::class.java)
        binding.model = viewModel
        // TODO: Use the ViewModel
    }

}