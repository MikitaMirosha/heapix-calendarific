package com.heapix.calendarific.ui.holidays.adapter.holiday

import android.view.View
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday_card.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class HolidayViewHolder(
    itemView: View,
    private val holidayResponsePublishSubject: PublishSubject<HolidayResponse>
) : BaseViewHolder<HolidayResponse>(itemView) {

    override fun bind(model: HolidayResponse) {
        setupHolidayName(model)
        setupHolidayDate(model)
        setupHolidayDescription(model)
        setupHolidayStatusImage(model)
        setupClickListener(model)
    }

    private fun setupHolidayName(holidayResponse: HolidayResponse) {
        vTvHolidayName.text = holidayResponse.name
    }

    private fun setupHolidayDate(holidayResponse: HolidayResponse) {
        val parser = DateTime(holidayResponse.date?.iso)
        val formatter = DateTimeFormat.forPattern("d MMM yyyy")
        vTvHolidayDate.text = parser.toString(formatter)
    }

    private fun setupHolidayDescription(holidayResponse: HolidayResponse) {
        vTvHolidayDescription.text = holidayResponse.description
    }

    private fun setupHolidayStatusImage(holidayResponse: HolidayResponse) {
        if (holidayResponse.isNotHolidayPassed) {
            vIvHolidayStatus.setImageResource(R.drawable.ic_clock)
        } else {
            vIvHolidayStatus.setImageResource(R.drawable.ic_checked)
        }
    }

    private fun setupClickListener(holidayResponse: HolidayResponse) {
        setOnClickListener {
            holidayResponsePublishSubject.onNext(holidayResponse)
        }
    }

}