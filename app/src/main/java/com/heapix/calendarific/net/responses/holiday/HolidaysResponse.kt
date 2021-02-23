package com.heapix.calendarific.net.responses.holiday

data class HolidaysResponse(
    var holidays: MutableList<HolidayResponse>? = mutableListOf()
)