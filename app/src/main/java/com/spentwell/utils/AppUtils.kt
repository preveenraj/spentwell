package com.spentwell.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import java.text.NumberFormat
import java.util.*

object AppUtils {
    fun isEarningsSet(context: Context): Boolean {
        val earnings = SharedPrefUtils.getSharedPreferences(context)
            .getFloat(SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS, 0.0f)
        return earnings != 0.0f
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