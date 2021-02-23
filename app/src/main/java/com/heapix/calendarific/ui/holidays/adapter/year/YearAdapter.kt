package com.heapix.calendarific.ui.holidays.adapter.year

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class YearAdapter : BaseListAdapter<Int>() {

    private val yearPublishSubject: PublishSubject<Int> = PublishSubject.create()
    val yearItemClickObservable: Observable<Int> = yearPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Int> {
        return YearViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday_parameter, parent, false),
            yearPublishSubject
        )
    }

}