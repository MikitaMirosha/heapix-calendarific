package com.heapix.calendarific.ui.initial

import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpView

interface InitialView : BaseMvpView {
    fun updateCountries(countryResponseList: MutableList<CountryResponse>)

    fun showCountryList()
    fun hideCountryList()

    fun showYearList(year: Int)
    fun hideYearList()

    fun showChosenCountryName(countryName: String?)
    fun showChosenYear(year: Int)

    fun openNavigationActivity()
}