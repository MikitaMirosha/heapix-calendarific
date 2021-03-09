package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.utils.preferences.year

class YearRepo(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val MIN_YEAR: Int = 2000
        private const val MAX_YEAR: Int = 2049
    }

    fun getAllYears(): MutableList<Int> = (MIN_YEAR..MAX_YEAR).toMutableList()

    fun getYear(): Int = sharedPreferences.year

    fun saveYear(year: Int) {
        sharedPreferences.year = year
    }

    fun isYearInStorage(): Boolean = getYear() != 0
}