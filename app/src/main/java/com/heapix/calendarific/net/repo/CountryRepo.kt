package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.net.services.CountryService
import com.heapix.calendarific.utils.pref.countryName
import com.heapix.calendarific.utils.pref.iso
import com.heapix.calendarific.utils.pref.year
import io.reactivex.Observable

class CountryRepo(
    private val api: CountryService,
    private val sharedPreferences: SharedPreferences
) {

    fun getAllCountries(): Observable<MutableList<CountryResponse>> {
        return api.getAllCountries().map { holidayCommonResponse ->
            holidayCommonResponse.response?.countries?.map { countryResponse ->
                countryResponse
            }?.toMutableList()
        }
    }

    fun getIso(): String = sharedPreferences.iso ?: ""

    fun saveIso(iso: String?) {
        sharedPreferences.iso = iso
    }

    fun getYear(): Int = sharedPreferences.year

    fun saveYear(year: Int) {
        sharedPreferences.year = year
    }

    fun getCountryName(): String = sharedPreferences.countryName ?: ""

    fun saveCountryName(countryName: String?) {
        sharedPreferences.countryName = countryName
    }
}