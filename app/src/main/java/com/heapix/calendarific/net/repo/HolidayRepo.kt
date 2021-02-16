package com.heapix.calendarific.net.repo

import com.heapix.calendarific.net.responses.holiday.HolidayDetailsResponse
import com.heapix.calendarific.net.services.HolidayService
import io.reactivex.Observable

class HolidayRepo(private val api: HolidayService) {

    fun getAllHolidays(
        country: String,
        year: Int
    ): Observable<MutableList<HolidayDetailsResponse>> {
        return api.getAllHolidays(country, year).map { holidayDetailsResponse ->
            holidayDetailsResponse.response?.holidays
        }
    }
}