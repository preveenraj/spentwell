package com.spentwell.utils

import android.content.Context

object AppUtils {
    fun isEarningsSet(context: Context): Boolean {
        val earnings = SharedPrefUtils.getSharedPreferences(context)
            .getInt(SharedPrefUtils.SHARED_PREFS_KEY_EARNINGS, 0)
        return earnings != 0
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
}