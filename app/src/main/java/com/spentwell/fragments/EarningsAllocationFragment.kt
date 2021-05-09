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
        binding.lifecycleOwner = this
        necessities = binding.allocationContainer.necessitiesContainer;
        savings = binding.allocationContainer.savingsContainer;
        luxuries = binding.allocationContainer.luxuriesContainer;
        setViews()

        return binding.root
    }

    private fun setViews() {

        binding.btProceed.setOnClickListener {
            val necessitiesValue = necessities.necessitiesSeekBar.progress;
            val savingsValue = savings.savingsSeekBar.progress;
            val luxuriesValue = luxuries.luxuriesSeekBar.progress;

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
        val n = necessities.necessitiesSeekBar.progress;
        val s = savings.savingsSeekBar.progress;
        val l = luxuries.luxuriesSeekBar.progress;

        val overAll = binding.overAllProgressContainer.overAllProgressValue;
        val overAllCard = binding.overAllProgressBarContainer.overAllProgressCard;
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
        necessities.necessitiesSeekBar.progress = necessitiesValueStored;
        necessities.necessitiesValue.text = necessitiesValueStored.toString();

        necessities.necessitiesSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val sumOfAllAllocations = setOverAllProgress()
                necessities.necessitiesValue.text = "$i"
                if (b) {
                    necessities.necessitiesSeekBar.progress = (i/5).toInt() * 5
                }
                if (b && (sumOfAllAllocations > 100)) {
                    necessities.necessitiesSeekBar.progress= 100 - (sumOfAllAllocations - i)
                }
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
        val luxuriesValueStored = allocationPref.getInt("luxuries", 30) ?: 0;

        luxuries.luxuriesSeekBar.progress = luxuriesValueStored
        luxuries.luxuriesValue.text = luxuriesValueStored.toString()
        luxuries.luxuriesSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val sumOfAllAllocations = setOverAllProgress()
                luxuries.luxuriesValue.text = "$i"
                if (b) {
                    luxuries.luxuriesSeekBar.progress = (i/5).toInt() * 5
                }
                if (b && (sumOfAllAllocations > 100)) {
                    luxuries.luxuriesSeekBar.progress = 100 - (sumOfAllAllocations - i)
                }
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
        val savingsValueStored = allocationPref.getInt("savings", 20)  ?: 0;
        savings.savingsSeekBar.progress = savingsValueStored
        savings.savingsValue.text = savingsValueStored.toString()

        savings.savingsSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                val sumOfAllAllocations = setOverAllProgress()
                savings.savingsValue.text = "$i"
                if (b) {
                    savings.savingsSeekBar.progress = (i/5).toInt() * 5
                }
                if (b && (sumOfAllAllocations > 100)) {
                    savings.savingsSeekBar.progress = 100 - (sumOfAllAllocations - i)
                }
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

        setOverAllProgress()

    }
}
