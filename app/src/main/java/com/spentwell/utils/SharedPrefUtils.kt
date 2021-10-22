package com.spentwell.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtils {
    const val SHARED_PREF_KEY_NECESSITIES = "necessities"
    const val SHARED_PREFS_KEY_LUXURIES = "luxuries"
    const val SHARED_PREFS_KEY_SAVINGS = "savings"
    const val SHARED_PREFS_KEY_EARNINGS = "earnings"

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            "SpentWell",
            Context.MODE_PRIVATE
        );
    }
}