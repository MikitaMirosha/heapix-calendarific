package com.heapix.calendarific.ui.holidays

import com.heapix.calendarific.net.responses.country.CountryDetailsResponse
import com.heapix.calendarific.net.responses.holiday.HolidayDetailsResponse
import com.heapix.calendarific.ui.base.BaseMvpView

interface HolidaysView : BaseMvpView {
    fun updateHolidays(holidayDetailsResponseList: MutableList<HolidayDetailsResponse>)
    fun updateCountries(countryDetailsResponseList: MutableList<CountryDetailsResponse>)
    fun updateYears(yearList: MutableList<Int>)
    fun showCountryListBottomSheet()
    fun showYearListBottomSheet()
    fun showSelectCountryAndYear()
    fun hideSelectCountryAndYear()
}