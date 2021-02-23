package com.heapix.calendarific.ui.holidays

import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.BaseMvpView

interface HolidaysView : BaseMvpView {
    fun updateHolidays(holidayResponseList: MutableList<HolidayResponse>)
    fun updateCountries(countryResponseList: MutableList<CountryResponse>)
    fun updateYears(yearList: MutableList<Int>)
    fun showCountryListBottomSheet()
    fun showYearListBottomSheet()
    fun showCurrentCountryName(countryName: String?)
    fun showCurrentYear()
    fun updateHolidayStatusImage()
}