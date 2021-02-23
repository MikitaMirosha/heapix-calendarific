package com.heapix.calendarific.net.repo

import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.net.services.HolidayService
import io.reactivex.Observable

class HolidayRepo(private val api: HolidayService) {

    fun getAllHolidays(
        country: String,
        year: Int
    ): Observable<MutableList<HolidayResponse>> {
        return api.getAllHolidays(country, year).map { holidayResponse ->
            holidayResponse.response?.holidays
        }
    }

    fun getHolidayDate(holidayResponse: HolidayResponse) = holidayResponse.date?.iso

}