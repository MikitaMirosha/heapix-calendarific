package com.heapix.calendarific.ui.holidays.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.model.Holidays
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class HolidaysAdapter : BaseListAdapter<Holidays>() {

    private val holidaysPublishSubject: PublishSubject<Holidays> = PublishSubject.create()
    val holidaysItemClickObservable: Observable<Holidays> = holidaysPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Holidays> {
        return HolidaysViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday, parent, false),
            holidaysPublishSubject
        )
    }

}