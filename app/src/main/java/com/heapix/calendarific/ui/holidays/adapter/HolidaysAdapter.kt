package com.heapix.calendarific.ui.holidays.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.holiday.HolidayDetailsResponse
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HolidaysAdapter : BaseListAdapter<HolidayDetailsResponse>() {

    private val holidayPublishSubject: PublishSubject<HolidayDetailsResponse> =
        PublishSubject.create()
    val holidayItemClickObservable: Observable<HolidayDetailsResponse> = holidayPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HolidayDetailsResponse> {
        return HolidaysViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday, parent, false),
            holidayPublishSubject
        )
    }

}