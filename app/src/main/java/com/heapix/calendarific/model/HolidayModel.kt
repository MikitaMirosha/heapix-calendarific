package com.heapix.calendarific.model

data class HolidayModel(
    var name: String? = null,
    var description: String? = null,
    var country: CountryModel? = null,
    var date: DateModel? = null,
    var type: MutableList<String>? = null,
    var locations: String? = null,
    var states: String? = null
)