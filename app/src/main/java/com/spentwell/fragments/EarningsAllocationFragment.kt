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
import com.spentwell.databinding.FragmentEarningsAllocationBinding
import com.spentwell.viewmodels.EarningsAllocationViewModel

class EarningsAllocationFragment : Fragment() {

    companion object {
        fun newInstance() = EarningsAllocationFragment()
    }

    private lateinit var viewModel: EarningsAllocationViewModel
    private lateinit var binding: FragmentEarningsAllocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_earnings_allocation, container, false
        )
        setViews()
        return binding.root
    }

    private fun setViews() {
        binding.btProceed.setOnClickListener {
            val navDirections = EarningsAllocationFragmentDirections.actionEarningsAllocationFragmentToSetEarningsFragment()
            findNavController().navigate(navDirections)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EarningsAllocationViewModel::class.java)

    }

}