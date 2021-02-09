package com.heapix.calendarific.net.repo

import com.heapix.calendarific.model.HolidayModel
import com.heapix.calendarific.net.services.HolidaysService
import io.reactivex.Observable

class HolidaysRepo(private val api: HolidaysService) {

    fun getAllHolidays(): Observable<MutableList<HolidayModel>> {
        return api.getAllHolidays("by", 2019).map { holidayResponseModel ->
            holidayResponseModel.response?.holidays ?: mutableListOf()
        }
    }
}