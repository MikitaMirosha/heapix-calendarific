package com.heapix.calendarific.ui.holidays.adapter

import android.view.View
import com.heapix.calendarific.net.responses.holiday.HolidayDetailsResponse
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class HolidaysViewHolder(
    itemView: View,
    private val holidayPublishSubject: PublishSubject<HolidayDetailsResponse>
) : BaseViewHolder<HolidayDetailsResponse>(itemView) {

    override fun bind(model: HolidayDetailsResponse) {
        setupHolidayName(model)
        setupHolidayDate(model)
        setupHolidayDescription(model)
        setupClickListener(model)
    }

    private fun setupHolidayName(holidayDetailsResponse: HolidayDetailsResponse) {
        vTvHolidayName.text = holidayDetailsResponse.name
    }

    private fun setupHolidayDate(holidayDetailsResponse: HolidayDetailsResponse) {
        val parser = DateTime(holidayDetailsResponse.date?.iso)
        val formatter = DateTimeFormat.forPattern("d MMM yyyy")
        vTvHolidayDate.text = parser.toString(formatter)
    }

    private fun setupHolidayDescription(holidayDetailsResponse: HolidayDetailsResponse) {
        vTvHolidayDescription.text = holidayDetailsResponse.description
    }

    private fun setupClickListener(holidayDetailsResponse: HolidayDetailsResponse) {
        setOnClickListener {
            holidayPublishSubject.onNext(holidayDetailsResponse)
        }
    }

}