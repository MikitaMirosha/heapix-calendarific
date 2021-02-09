package com.heapix.calendarific.ui.holidays

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.calendarific.R
import com.heapix.calendarific.model.HolidayModel
import com.heapix.calendarific.ui.base.BaseMvpFragment
import com.heapix.calendarific.ui.holidays.adapter.HolidaysAdapter
import kotlinx.android.synthetic.main.fragment_holidays.*

class HolidaysFragment : BaseMvpFragment(), HolidaysView {

    @InjectPresenter
    lateinit var holidaysPresenter: HolidaysPresenter

    lateinit var holidaysAdapter: HolidaysAdapter

    override fun getLayoutId(): Int = R.layout.fragment_holidays

    override fun onViewCreated(view: View) {
        setupHolidaysAdapter()
        holidaysPresenter.onCreate(holidaysAdapter.holidayItemClickObservable)
    }

    companion object {
        fun newInstance(): HolidaysFragment = HolidaysFragment()
    }

    private fun setupHolidaysAdapter() {
        holidaysAdapter = HolidaysAdapter()
        vRvHolidaysList.adapter = holidaysAdapter
    }

    override fun updateHolidays(holidayModelList: MutableList<HolidayModel>) {
        holidaysAdapter.setItems(holidayModelList)
    }

}