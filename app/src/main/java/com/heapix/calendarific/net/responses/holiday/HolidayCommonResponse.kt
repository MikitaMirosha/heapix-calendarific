package com.heapix.calendarific.net.responses.holiday

import com.heapix.calendarific.net.responses.MetaResponse

data class HolidayCommonResponse(
    var meta: MetaResponse? = null,
    var response: HolidaysResponse? = null
)