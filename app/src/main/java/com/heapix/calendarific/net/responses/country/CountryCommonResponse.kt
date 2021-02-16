package com.heapix.calendarific.net.responses.country

import com.heapix.calendarific.net.responses.MetaResponse

data class CountryCommonResponse(
    var meta: MetaResponse? = null,
    var response: CountriesResponse? = null
)