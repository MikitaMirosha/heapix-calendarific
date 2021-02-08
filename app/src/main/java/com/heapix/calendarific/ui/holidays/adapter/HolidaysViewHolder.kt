package com.heapix.calendarific.ui.holidays.adapter

import android.view.View
import com.heapix.calendarific.model.Holidays
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class HolidaysViewHolder(
    itemView: View,
    private val holidaysPublishSubject: PublishSubject<Holidays>
) : BaseViewHolder<Holidays>(itemView) {

    override fun bind(model: Holidays) {
        setupHolidaysName(model)
        setupHolidaysDate(model)
        setupHolidaysDescription(model)
        setupClickListener(model)
    }

    private fun setupHolidaysName(holidays: Holidays) {
        vTvHolidaysName.text = (holidays as Holidays.Details).detailsModel?.name
    }

    private fun setupHolidaysDate(holidays: Holidays) {
        val parser = DateTime((holidays as Holidays.Details).detailsModel?.date)
        val formatter = DateTimeFormat.forPattern("d MMM, yyyy")
        vTvHolidaysDate.text = parser.toString(formatter)
    }

    private fun setupHolidaysDescription(holidays: Holidays) {
        vTvHolidaysDescription.text = (holidays as Holidays.Details).detailsModel?.description
    }

    private fun setupClickListener(holidays: Holidays) {
        setOnClickListener {
            holidaysPublishSubject.onNext(holidays)
        }
    }

}