package com.heapix.calendarific.model

class DetailsModel(
    var name: String? = null,
    var description: String? = null,
    var date: DateModel? = null,
    var type: MutableList<String>? = null
) : HolidaysModel()