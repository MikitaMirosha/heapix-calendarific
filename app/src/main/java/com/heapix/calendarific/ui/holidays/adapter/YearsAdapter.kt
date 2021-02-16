package com.heapix.calendarific.ui.holidays.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class YearsAdapter : BaseListAdapter<Int>() {

    private val yearPublishSubject: PublishSubject<Int> = PublishSubject.create()
    val yearItemClickObservable: Observable<Int> = yearPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Int> {
        return YearsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday_parameter, parent, false),
            yearPublishSubject
        )
    }

}