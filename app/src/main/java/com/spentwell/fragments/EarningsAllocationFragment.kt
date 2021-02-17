package com.spentwell.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.databinding.FragmentEarningsAllocationBinding
import com.spentwell.viewmodels.EarningsAllocationViewModel
import kotlinx.android.synthetic.main.fragment_earnings_allocation.view.*


class EarningsAllocationFragment : Fragment() {

    companion object {
        fun newInstance() = EarningsAllocationFragment()
    }

    private lateinit var viewModel: EarningsAllocationViewModel
    private lateinit var binding: FragmentEarningsAllocationBinding

    private lateinit var allocationPref: SharedPreferences

    private lateinit var necessities: LinearLayout;
    private lateinit var  savings: LinearLayout;
    private lateinit var luxuries: LinearLayout;

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
        necessities = binding.allocationContainer.necessitiesContainer;
        savings = binding.allocationContainer.savingsContainer;
        luxuries = binding.allocationContainer.luxuriesContainer;

        binding.btProceed.setOnClickListener {
            val editor: SharedPreferences.Editor = allocationPref.edit()
            editor.putInt("necessities", necessities.necessitiesSeekBar.progress)
            editor.putInt("savings", savings.savingsSeekBar.progress)
            editor.putInt("luxuries", luxuries.luxuriesSeekBar.progress)
            editor.commit()

            val navDirections = EarningsAllocationFragmentDirections.actionEarningsAllocationFragmentToSetEarningsFragment()
            findNavController().navigate(navDirections)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EarningsAllocationViewModel::class.java)
        allocationPref = this.requireActivity().getSharedPreferences("earningsAllocation", Context.MODE_PRIVATE);
        setSeekBars()

    }

    private fun setSeekBars() {

//        NECESSITIES
        val necessitiesValueStored = allocationPref.getInt("necessities", 0)  ?: 0;
        necessities.necessitiesSeekBar.progress = necessitiesValueStored;
        necessities.necessitiesValue.text = necessitiesValueStored.toString();

        necessities.necessitiesSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                necessities.necessitiesValue.text = "$i"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"stop tracking", Toast.LENGTH_SHORT).show()
            }
        })
//      SAVINGS
        val savingsValueStored = allocationPref.getInt("savings", 0)  ?: 0;
        savings.savingsSeekBar.progress = savingsValueStored
        savings.savingsValue.text = savingsValueStored.toString()

        savings.savingsSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                savings.savingsValue.text = "$i"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"stop tracking", Toast.LENGTH_SHORT).show()
            }

        })
//      LUXURIES
        val luxuriesValueStored = allocationPref.getInt("luxuries", 0) ?: 0;

        luxuries.luxuriesSeekBar.progress = luxuriesValueStored
        luxuries.luxuriesValue.text = luxuriesValueStored.toString()
        luxuries.luxuriesSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                luxuries.luxuriesValue.text = "$i"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"start tracking",Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
//                Toast.makeText(applicationContext,"stop tracking", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
