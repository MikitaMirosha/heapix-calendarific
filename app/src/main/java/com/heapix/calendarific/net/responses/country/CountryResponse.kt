package com.heapix.calendarific.net.responses.country

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("country_name") var countryName: String? = null,
    @SerializedName("iso-3166") var iso: String? = null,
    @SerializedName("total_holidays") var totalHolidays: Int? = null,
    @SerializedName("supported_languages") var supportedLanguages: Int? = null,
    var uuid: String? = null
)