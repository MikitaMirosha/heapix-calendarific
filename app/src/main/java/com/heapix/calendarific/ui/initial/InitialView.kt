package com.heapix.calendarific.ui.initial

import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpView

interface InitialView : BaseMvpView {
    fun updateCountries(countryResponseList: MutableList<CountryResponse>)
    fun updateYears(yearList: MutableList<Int>)
    fun showCountryList()
    fun hideCountryList()
    fun showChosenCountryName(countryName: String?)
    fun showChosenYear(year: Int)
    fun openNavigationActivity()
    fun toggleBottomSheet()
}