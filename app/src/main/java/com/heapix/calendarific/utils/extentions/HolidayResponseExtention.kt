package com.heapix.calendarific.utils.extentions

import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import org.joda.time.LocalDate

fun HolidayResponse.filterNotPassedHolidays() {
    if (this.date?.datetime?.year?.compareTo(LocalDate().yearOfEra) ?: 0 > 0) {
        this.isNotHolidayPassed = true
    }

    if (this.date?.datetime?.year == LocalDate().yearOfEra &&
        this.date?.datetime?.month?.compareTo(LocalDate().monthOfYear) ?: 0 > 0
    ) {
        this.isNotHolidayPassed = true
    }

    if (this.date?.datetime?.year == LocalDate().yearOfEra &&
        this.date?.datetime?.month == LocalDate().monthOfYear &&
        this.date?.datetime?.day?.compareTo(LocalDate().dayOfMonth) ?: 0 > 0
    ) {
        this.isNotHolidayPassed = true
    }
}