package com.heapix.calendarific.ui.holidays.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.model.HolidayModel
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class HolidaysAdapter : BaseListAdapter<HolidayModel>() {

    private val holidayPublishSubject: PublishSubject<HolidayModel> = PublishSubject.create()
    val holidayItemClickObservable: Observable<HolidayModel> = holidayPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HolidayModel> {
        return HolidaysViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday, parent, false),
            holidayPublishSubject
        )
    }

}