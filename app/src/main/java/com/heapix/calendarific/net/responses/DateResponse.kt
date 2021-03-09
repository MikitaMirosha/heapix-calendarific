package com.heapix.calendarific.net.responses

data class DateResponse(
    var iso: String? = null,
    var datetime: DateTimeResponse? = null
)