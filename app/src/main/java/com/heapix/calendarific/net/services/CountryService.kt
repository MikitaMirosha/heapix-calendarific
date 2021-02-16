package com.heapix.calendarific.net.services

import com.heapix.calendarific.net.responses.country.CountryCommonResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface CountryService {

    @GET("countries")
    fun getAllCountries(): Observable<CountryCommonResponse>
}