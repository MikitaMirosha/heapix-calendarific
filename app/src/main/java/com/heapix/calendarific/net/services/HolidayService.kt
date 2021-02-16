package com.heapix.calendarific.net.services

import com.heapix.calendarific.net.responses.holiday.HolidayCommonResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayService {

    @GET("holidays")
    fun getAllHolidays(
        @Query("country") country: String,
        @Query("year") year: Int
    ): Observable<HolidayCommonResponse>
}