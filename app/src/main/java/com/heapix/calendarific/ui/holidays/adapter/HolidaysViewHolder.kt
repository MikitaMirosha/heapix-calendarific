package com.heapix.calendarific.ui.holidays.adapter

import android.view.View
import com.heapix.calendarific.model.HolidayModel
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class HolidaysViewHolder(
    itemView: View,
    private val holidayPublishSubject: PublishSubject<HolidayModel>
) : BaseViewHolder<HolidayModel>(itemView) {

    override fun bind(model: HolidayModel) {
        setupHolidaysName(model)
        setupHolidaysDate(model)
        setupHolidaysDescription(model)
        setupClickListener(model)
    }

    private fun setupHolidaysName(holidayModel: HolidayModel) {
        vTvHolidaysName.text = holidayModel.name
    }

    private fun setupHolidaysDate(holidayModel: HolidayModel) {
        val parser = DateTime(holidayModel.date?.iso)
        val formatter = DateTimeFormat.forPattern("d MMM yyyy")
        vTvHolidaysDate.text = parser.toString(formatter)
    }

    private fun setupHolidaysDescription(holidayModel: HolidayModel) {
        vTvHolidaysDescription.text = holidayModel.description
    }

    private fun setupClickListener(holidayModel: HolidayModel) {
        setOnClickListener {
            holidayPublishSubject.onNext(holidayModel)
        }
    }

}