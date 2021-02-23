package com.heapix.calendarific.net.responses.holiday

import com.heapix.calendarific.net.responses.DateResponse

data class HolidayResponse(
    var name: String? = null,
    var description: String? = null,
    var date: DateResponse? = null,
    var type: MutableList<String>? = mutableListOf(),
    var locations: String? = null,
    var states: String? = null
)