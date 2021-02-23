package com.heapix.calendarific.ui.navigation

import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpView

interface NavigationView : BaseMvpView {
    fun updateCountries(countryResponseList: MutableList<CountryResponse>)
    fun showSelectionView()
    fun showCountryList()
    fun hideCountryList()
    fun showYearPicker()
    fun hideYearPicker()
    fun showHolidaysFragment()
    fun onNumberPickerValueChanged()
}