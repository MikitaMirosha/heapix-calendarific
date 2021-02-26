package com.heapix.calendarific.utils.pref

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils {

    companion object {
        fun getSharedPreferences(context: Context) =
            context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)!!

        private const val PREFERENCES = "PREFERENCES"

        const val ISO: String = "ISO"
        const val YEAR: String = "YEAR"
        const val COUNTRY_NAME: String = "COUNTRY_NAME"
    }
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

var SharedPreferences.iso: String?
    get() = getString(PreferencesUtils.ISO, "")
    set(value) = editMe { it.putString(PreferencesUtils.ISO, value) }

var SharedPreferences.year: Int
    get() = getInt(PreferencesUtils.YEAR, 0)
    set(value) = editMe { it.putInt(PreferencesUtils.YEAR, value) }

var SharedPreferences.countryName: String?
    get() = getString(PreferencesUtils.COUNTRY_NAME, "")
    set(value) = editMe { it.putString(PreferencesUtils.COUNTRY_NAME, value) }
