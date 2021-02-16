package com.heapix.calendarific.net.responses.country

data class CountriesResponse(
    var url: String? = null,
    var countries: MutableList<CountryDetailsResponse>? = mutableListOf()
)