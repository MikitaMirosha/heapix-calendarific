package com.heapix.calendarific.ui.holidays

import com.heapix.calendarific.model.Holidays
import com.heapix.calendarific.model.HolidaysModel
import com.heapix.calendarific.ui.base.BaseMvpView

interface HolidaysView : BaseMvpView {
    fun updateHolidays(holidaysModelList: MutableList<Holidays>)
}