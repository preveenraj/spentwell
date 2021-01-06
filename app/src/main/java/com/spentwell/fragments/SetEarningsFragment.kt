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
import com.spentwell.databinding.FragmentSetEarningsBinding
import com.spentwell.viewmodels.SetEarningsViewModel

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
        setViews()
        return binding.root
    }

    private fun setViews() {
        binding.btProceed.setOnClickListener{
            val navDirections = SetEarningsFragmentDirections.actionSetEarningsFragmentToDashboardFragment()
            findNavController().navigate(navDirections)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetEarningsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}