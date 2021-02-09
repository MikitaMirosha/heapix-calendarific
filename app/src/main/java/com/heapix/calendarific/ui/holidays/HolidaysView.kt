package com.heapix.calendarific.ui.holidays

import com.heapix.calendarific.model.HolidayModel
import com.heapix.calendarific.ui.base.BaseMvpView

interface HolidaysView : BaseMvpView {
    fun updateHolidays(holidayModelList: MutableList<HolidayModel>)
}