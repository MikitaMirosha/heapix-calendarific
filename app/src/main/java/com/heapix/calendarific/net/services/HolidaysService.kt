package com.heapix.calendarific.net.services

import com.heapix.calendarific.model.HolidayResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidaysService {

    @GET("holidays")
    fun getAllHolidays(
        @Query("country") country: String,
        @Query("year") year: Int
    ): Observable<HolidayResponseModel>
}