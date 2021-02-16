package com.heapix.calendarific.ui.holidays.adapter

import android.view.View
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday_parameter.*

class YearsViewHolder(
    itemView: View,
    private val yearPublishSubject: PublishSubject<Int>
) : BaseViewHolder<Int>(itemView) {

    override fun bind(model: Int) {
        setupYear(model)
        setupClickListener(model)
    }

    private fun setupYear(year: Int) {
        vTvParameterName.text = year.toString()
    }

    private fun setupClickListener(year: Int) {
        setOnClickListener {
            yearPublishSubject.onNext(year)
        }
    }

}