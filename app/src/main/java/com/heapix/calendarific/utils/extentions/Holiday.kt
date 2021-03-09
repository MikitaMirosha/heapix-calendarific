package com.heapix.calendarific.utils.extentions

import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import org.joda.time.DateTime

fun HolidayResponse.setupHolidayStatus() {
    setupHolidayStatusByPassedYears()
    setupHolidayStatusByPassedMonths()
    setupHolidayStatusByPassedDays()
}

fun HolidayResponse.setupHolidayStatusByPassedYears() {
    if (this.date?.datetime?.year?.compareTo(DateTime.now().yearOfEra) ?: 0 > 0) {
        this.isNotHolidayPassed = true
        return
    }
}

fun HolidayResponse.setupHolidayStatusByPassedMonths() {
    if (this.date?.datetime?.year == DateTime.now().yearOfEra &&
        this.date?.datetime?.month?.compareTo(DateTime.now().monthOfYear) ?: 0 > 0
    ) {
        this.isNotHolidayPassed = true
        return
    }
}

fun HolidayResponse.setupHolidayStatusByPassedDays() {
    if (this.date?.datetime?.year == DateTime.now().yearOfEra &&
        this.date?.datetime?.month == DateTime.now().monthOfYear &&
        this.date?.datetime?.day?.compareTo(DateTime.now().dayOfMonth) ?: 0 > 0
    ) {
        this.isNotHolidayPassed = true
        return
    }
}