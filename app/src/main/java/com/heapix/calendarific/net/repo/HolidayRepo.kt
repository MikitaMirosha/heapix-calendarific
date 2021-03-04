package com.heapix.calendarific.net.repo

import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.net.services.HolidayService
import com.heapix.calendarific.utils.extensions.filterEqualYearsAndMonthWithFutureDays
import com.heapix.calendarific.utils.extensions.filterEqualYearsWithFutureMonths
import com.heapix.calendarific.utils.extensions.filterFutureYears
import io.reactivex.Observable

class HolidayRepo(private val api: HolidayService) {

    fun getAllHolidays(
        country: String,
        year: Int
    ): Observable<MutableList<HolidayResponse>> {
        return api.getAllHolidays(country, year).map { holidayResponse ->
            holidayResponse.response?.holidays?.map {
                it.apply {
                    filterFutureYears()
                    filterEqualYearsWithFutureMonths()
                    filterEqualYearsAndMonthWithFutureDays()
                }
            }
            holidayResponse.response?.holidays
        }
    }

}

