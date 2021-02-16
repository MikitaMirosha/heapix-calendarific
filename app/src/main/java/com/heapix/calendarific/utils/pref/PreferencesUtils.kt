package com.heapix.calendarific.utils.pref

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils {

    companion object {
        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)!!

        private const val PREFERENCES = "PREFERENCES"

        const val COUNTRY: String = "COUNTRY"
        const val YEAR: String = "YEAR"
    }
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

var SharedPreferences.country: String?
    get() = getString(PreferencesUtils.COUNTRY, "")
    set(value) = editMe { it.putString(PreferencesUtils.COUNTRY, value) }

var SharedPreferences.year: Int
    get() = getInt(PreferencesUtils.YEAR, 0)
    set(value) = editMe { it.putInt(PreferencesUtils.YEAR, value) }