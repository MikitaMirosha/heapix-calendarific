package com.heapix.calendarific.utils.extentions

import com.heapix.calendarific.net.responses.country.CountryResponse

fun findCountryNameByIso(
    countryResponse: MutableList<CountryResponse>,
    iso: String
): String? {
    return countryResponse.firstOrNull {
        iso == it.iso
    }?.countryName
}