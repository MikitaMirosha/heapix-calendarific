package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.utils.pref.year

class YearRepo(private val sharedPreferences: SharedPreferences) {

    fun getAllYears(): MutableList<Int> = (2000..2049).toMutableList()

    fun getYear(): Int = sharedPreferences.year

    fun saveYear(year: Int) {
        sharedPreferences.year = year
    }
}