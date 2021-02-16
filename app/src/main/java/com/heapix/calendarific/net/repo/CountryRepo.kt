package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.net.responses.country.CountryDetailsResponse
import com.heapix.calendarific.net.services.CountryService
import com.heapix.calendarific.utils.pref.country
import io.reactivex.Observable

class CountryRepo(
    private val api: CountryService,
    private val sharedPreferences: SharedPreferences
) {

    fun getAllCountries(): Observable<MutableList<CountryDetailsResponse>> {
        return api.getAllCountries().map { holidayCommonResponse ->
            holidayCommonResponse.response?.countries?.map { countryDetailsResponse ->
                countryDetailsResponse
            }?.toMutableList()
        }
    }

    fun getCountryFromSharedPreferences(): String? = sharedPreferences.country

    fun saveCountryToSharedPreferences(country: String?) {
        sharedPreferences.country = country
    }
}