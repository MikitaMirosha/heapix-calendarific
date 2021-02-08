package com.heapix.calendarific.net.repo

import android.content.SharedPreferences
import com.heapix.calendarific.model.Holidays
import com.heapix.calendarific.net.services.HolidaysService
import io.reactivex.Observable

class HolidaysRepo(
    private val api: HolidaysService,
    private val sharedPreferences: SharedPreferences
) {

    fun getAllHolidays(): Observable<MutableList<Holidays>> = api.getAllHolidays()
}