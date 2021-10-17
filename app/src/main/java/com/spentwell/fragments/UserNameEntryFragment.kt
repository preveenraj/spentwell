package com.spentwell.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.spentwell.R
import com.spentwell.databinding.FragmentUserNameEntryBinding
import com.spentwell.utils.AppUtils
import com.spentwell.utils.SharedPrefUtils
import com.spentwell.viewmodels.UserNameEntryViewModel

class UserNameEntryFragment : Fragment() {

    companion object {
        fun newInstance() = UserNameEntryFragment()
    }

    private lateinit var viewModel: UserNameEntryViewModel
    private lateinit var binding: FragmentUserNameEntryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_name_entry, container, false
        )
        setViews()
        return binding.root
    }

    private fun setViews() {
        binding.etUserName.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    saveAndProceed()
                    true
                }
                else -> false
            }
        }
        binding.btProceed.setOnClickListener {
            saveAndProceed()
        }
    }

    private fun saveAndProceed() {
        if (binding.etUserName.text.isNotEmpty()) {
            SharedPrefUtils.getSharedPreferences(requireContext()).edit().putString(
                SharedPrefUtils.SHARED_PREFS_KEY_USER_NAME,
                binding.etUserName.text.toString()
            ).apply()
            AppUtils.hideKeyboard(requireContext(), requireView())
            val navDirections =
                UserNameEntryFragmentDirections.actionUserNameEntryFragmentToEarningsAllocationFragment()
            findNavController().navigate(navDirections)
        } else {
            Toast.makeText(
                requireContext(),
                "Please enter a valid name",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserNameEntryViewModel::class.java)
        binding.lifecycleOwner = this
        binding.model = viewModel

    }

}