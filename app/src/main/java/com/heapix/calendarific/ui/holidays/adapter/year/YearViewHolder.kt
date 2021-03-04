package com.heapix.calendarific.ui.holidays.adapter.year

import android.view.View
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_year_number.*

class YearViewHolder(
    itemView: View,
    private val yearPublishSubject: PublishSubject<Int>
) : BaseViewHolder<Int>(itemView) {

    override fun bind(model: Int) {
        setupYear(model)
        setupClickListener(model)
    }

    private fun setupYear(year: Int) {
        vTvYearNumberItem.text = year.toString()
    }

    private fun setupClickListener(year: Int) {
        setOnClickListener {
            yearPublishSubject.onNext(year)
        }
    }

}