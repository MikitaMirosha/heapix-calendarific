package com.heapix.calendarific.ui.holidays

import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.BaseMvpView

interface HolidaysView : BaseMvpView {
    fun updateHolidays(holidayResponseList: MutableList<HolidayResponse>)
    fun updateCountries(countryResponseList: MutableList<CountryResponse>)
    fun updateYears(yearList: MutableList<Int>)
    fun showCountryList()
    fun hideCountryList()
    fun showChosenCountryName(countryName: String?)
    fun showChosenYear(year: Int)
    fun toggleBottomSheet()
    fun onDoubleBackPressed()
}