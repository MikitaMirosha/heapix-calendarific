package com.heapix.calendarific.net.responses.country

import com.google.gson.annotations.SerializedName

data class CountryDetailsResponse(
    var country_name: String? = null,
    @SerializedName("iso-3166") var iso: String? = null,
    var total_holidays: Int? = null,
    var supported_languages: Int? = null,
    var uuid: String? = null
)