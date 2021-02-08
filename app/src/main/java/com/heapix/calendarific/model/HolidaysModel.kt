package com.heapix.calendarific.model

sealed class Holidays {
    class Details(val detailsModel: DetailsModel?) : Holidays()
    class Meta(val metaModel: MetaModel?) : Holidays()
    class Response(val responseModel: ResponseModel?) : Holidays()
}

open class HolidaysModel(
    var meta: MetaModel? = null,
    var response: ResponseModel? = null
) : Holidays()