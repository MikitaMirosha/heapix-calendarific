package com.heapix.calendarific.net.repo

import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.net.services.HolidayService
import io.reactivex.Observable
import org.joda.time.LocalDate

class HolidayRepo(private val api: HolidayService) {

    fun getAllHolidays(
        country: String,
        year: Int
    ): Observable<MutableList<HolidayResponse>> {
        return api.getAllHolidays(country, year).map { holidayResponse ->
            holidayResponse.response?.holidays?.map {

                if (it.date?.datetime?.year?.compareTo(LocalDate().yearOfEra) ?: 0 > 0) {
                    it.isNotHolidayPassed = true
                }

                if (it.date?.datetime?.year == LocalDate().yearOfEra &&
                    it.date?.datetime?.month?.compareTo(LocalDate().monthOfYear) ?: 0 > 0
                ) {
                    if (it.date?.datetime?.month == LocalDate().monthOfYear &&
                        it.date?.datetime?.day?.compareTo(LocalDate().dayOfMonth) ?: 0 > 0
                    ) {
                        it.isNotHolidayPassed = true
                    }
                    it.isNotHolidayPassed = true
                }

            }

            holidayResponse.response?.holidays
        }
    }

}