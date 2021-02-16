package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.utils.pref.year

class YearRepo(private val sharedPreferences: SharedPreferences) {

    fun getAllYears(): MutableList<Int> = (2000..2049).toMutableList()

    fun getYearFromSharedPreferences(): Int = sharedPreferences.year

    fun saveYearToSharedPreferences(year: Int) {
        sharedPreferences.year = year
    }
}