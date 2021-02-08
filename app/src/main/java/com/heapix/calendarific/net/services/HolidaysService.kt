package com.heapix.calendarific.net.services

import com.heapix.calendarific.model.Holidays
import io.reactivex.Observable
import retrofit2.http.GET

interface HolidaysService {

    @GET("/holidays?&api_key=284ff11936b12dcf843b42ad9bafd689f786ffbf&country=US&year=2019")
    fun getAllHolidays(): Observable<MutableList<Holidays>>
}