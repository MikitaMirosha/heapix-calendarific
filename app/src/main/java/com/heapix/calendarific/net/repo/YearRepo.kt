package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.utils.preferences.year

class YearRepo(private val sharedPreferences: SharedPreferences) {

    private val minYear: Int = 2000
    private val maxYear: Int = 2049

    fun getAllYears(): MutableList<Int> = (minYear..maxYear).toMutableList()

    fun getYear(): Int = sharedPreferences.year

    fun saveYear(year: Int) {
        sharedPreferences.year = year
    }

    fun isYearInStorage(): Boolean = getYear() != 0
}