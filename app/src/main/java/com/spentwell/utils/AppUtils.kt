package com.spentwell.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.util.*

object AppUtils {
    fun isEarningsSet(context: Context): Boolean {
        val earnings = SharedPrefUtils.getSharedPreferences(context)
            .getFloat(SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS, 0.0f)
        return earnings != 0.0f
    }

    fun isUserNameSet(context: Context): Boolean {
        val earnings = SharedPrefUtils.getSharedPreferences(context)
            .getString(SharedPrefUtils.SHARED_PREFS_KEY_USER_NAME, "")
        return earnings.isNotNullOrEmpty()
    }

    fun hideKeyboard(context: Context, view: View) {
        // Hide the keyboard.
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isEarningsAllocated(context: Context): Boolean {
        val luxuries = SharedPrefUtils.getSharedPreferences(context)
            .getInt(SharedPrefUtils.SHARED_PREFS_KEY_LUXURIES, 0)
        val savings = SharedPrefUtils.getSharedPreferences(context)
            .getInt(SharedPrefUtils.SHARED_PREFS_KEY_SAVINGS, 0)
        val necessities = SharedPrefUtils.getSharedPreferences(context)
            .getInt(SharedPrefUtils.SHARED_PREF_KEY_NECESSITIES, 0)
        return luxuries != 0 && savings != 0 && necessities != 0
    }

    fun getFormattedCurrencyString(amount: Double): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("INR")
        return format.format(amount)
    }

    fun convertDpToPixels(dp: Float, resources: Resources): Float {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
        return px
    }
}