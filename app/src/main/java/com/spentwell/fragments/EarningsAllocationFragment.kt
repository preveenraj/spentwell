package com.spentwell.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.databinding.FragmentEarningsAllocationBinding
import com.spentwell.utils.SharedPrefUtils
import com.spentwell.viewmodels.EarningsAllocationViewModel

class EarningsAllocationFragment : Fragment() {

    companion object {
        fun newInstance() = EarningsAllocationFragment()
    }

    private lateinit var viewModel: EarningsAllocationViewModel
    private lateinit var binding: FragmentEarningsAllocationBinding

    private lateinit var allocationPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_earnings_allocation, container, false
        )
        binding.lifecycleOwner = this
        setButton()

        return binding.root
    }

    private fun setButton() {

        binding.btProceed.setOnClickListener {
            val necessitiesValue = binding.allocationContainer.necessitiesSeekBar.progress;
            val savingsValue = binding.allocationContainer.savingsSeekBar.progress;
            val luxuriesValue = binding.allocationContainer.luxuriesSeekBar.progress;

            if( (necessitiesValue + savingsValue + luxuriesValue) == 100 ) {
                val editor: SharedPreferences.Editor = allocationPref.edit()
                editor.putInt(SharedPrefUtils.SHARED_PREF_KEY_NECESSITIES, necessitiesValue)
                editor.putInt(SharedPrefUtils.SHARED_PREFS_KEY_SAVINGS, savingsValue)
                editor.putInt(SharedPrefUtils.SHARED_PREFS_KEY_LUXURIES, luxuriesValue)
                editor.commit()

                val navDirections =
                    EarningsAllocationFragmentDirections.actionEarningsAllocationFragmentToSetEarningsFragment()
                findNavController().navigate(navDirections)
            } else {
                Toast.makeText(
                    this.requireActivity(),
                    "Oops... Your allocations doesn't add up to 100",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EarningsAllocationViewModel::class.java)
        binding.model = viewModel
        allocationPref = SharedPrefUtils.getSharedPreferences(requireContext())
        setSeekBars()

    }

    private fun setOverAllProgress():Int {
        val n = binding.allocationContainer.necessitiesSeekBar.progress;
        val s = binding.allocationContainer.savingsSeekBar.progress;
        val l = binding.allocationContainer.luxuriesSeekBar.progress;

        val overAll = binding.overAllProgressValue;
        val overAllCard = binding.overAllProgressCard;
        val sum = (n+s+l);
        overAll.text = " ${sum}/100"
        val layoutParams = overAllCard!!.layoutParams
        if(sum == 0) {
            layoutParams.width = 0
        } else {
            overAllCard.visibility = View.VISIBLE
            layoutParams.width =
                (sum * 2 * requireContext().resources.displayMetrics.density).toInt()
        }
        overAllCard.layoutParams = layoutParams
        if(sum == 100) {
            viewModel.toggleProceedButton(true)
            overAllCard.setCardBackgroundColor(requireContext().getColor(R.color.colorPositive))
        } else {
            viewModel.toggleProceedButton(false)
            overAllCard.setCardBackgroundColor(requireContext().getColor(R.color.colorNegative))
        }
        return sum
    }


    private fun setSeekBars() {

//        NECESSITIES
        val necessitiesValueStored = allocationPref.getInt("necessities", 50)  ?: 0;
        binding.allocationContainer.necessitiesSeekBar.progress = necessitiesValueStored;
        binding.allocationContainer.necessitiesValue.text = necessitiesValueStored.toString();

        binding.allocationContainer.necessitiesSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val sumOfAllAllocations = setOverAllProgress()
                binding.allocationContainer.necessitiesValue.text = "$i"
                if (b) {
                    binding.allocationContainer.necessitiesSeekBar.progress = (i/5).toInt() * 5
                }
                if (b && (sumOfAllAllocations > 100)) {
                    binding.allocationContainer.necessitiesSeekBar.progress= 100 - (sumOfAllAllocations - i)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })


//      SAVINGS
        val savingsValueStored = allocationPref.getInt("savings", 20)  ?: 0;
        binding.allocationContainer.savingsSeekBar.progress = savingsValueStored
        binding.allocationContainer.savingsValue.text = savingsValueStored.toString()

        binding.allocationContainer.savingsSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val sumOfAllAllocations = setOverAllProgress()
                binding.allocationContainer.savingsValue.text = "$i"
                if (b) {
                    binding.allocationContainer.savingsSeekBar.progress = (i/5).toInt() * 5
                }
                if (b && (sumOfAllAllocations > 100)) {
                    binding.allocationContainer.savingsSeekBar.progress = 100 - (sumOfAllAllocations - i)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

//      LUXURIES
        val luxuriesValueStored = allocationPref.getInt("luxuries", 30) ?: 0;

        binding.allocationContainer.luxuriesSeekBar.progress = luxuriesValueStored
        binding.allocationContainer.luxuriesValue.text = luxuriesValueStored.toString()
        binding.allocationContainer.luxuriesSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val sumOfAllAllocations = setOverAllProgress()
                binding.allocationContainer.luxuriesValue.text = "$i"
                if (b) {
                    binding.allocationContainer.luxuriesSeekBar.progress = (i/5).toInt() * 5
                }
                if (b && (sumOfAllAllocations > 100)) {
                    binding.allocationContainer.luxuriesSeekBar.progress = 100 - (sumOfAllAllocations - i)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })



        setOverAllProgress()

    }
}
