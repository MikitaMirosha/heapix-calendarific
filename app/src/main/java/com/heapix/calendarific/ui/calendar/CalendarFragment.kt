package com.heapix.calendarific.ui.calendar

import android.view.View
import com.heapix.calendarific.R
import com.heapix.calendarific.ui.base.BaseMvpFragment

class CalendarFragment : BaseMvpFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_calendar

    override fun onViewCreated(view: View) {}

    companion object {
        fun newInstance(): CalendarFragment = CalendarFragment()
    }

}