package com.heapix.calendarific.ui.holidays.adapter.holiday

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class HolidayAdapter : BaseListAdapter<HolidayResponse>() {

    private val holidayPublishSubject: PublishSubject<HolidayResponse> =
        PublishSubject.create()
    val holidayItemClickObservable: Observable<HolidayResponse> =
        holidayPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HolidayResponse> {
        return HolidayViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday, parent, false),
            holidayPublishSubject
        )
    }

}