package com.heapix.calendarific.ui.holidays

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.BaseMvpFragment
import com.heapix.calendarific.ui.holidays.adapter.country.CountryAdapter
import com.heapix.calendarific.ui.holidays.adapter.holiday.HolidayAdapter
import com.heapix.calendarific.ui.holidays.adapter.year.YearAdapter
import com.heapix.calendarific.utils.view.hide
import com.heapix.calendarific.utils.view.show
import com.insspring.poifox.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_holidays.*
import kotlinx.android.synthetic.main.view_holiday_parameters.*
import kotlinx.android.synthetic.main.view_number_picker.*
import kotlinx.android.synthetic.main.view_selection.*


class HolidaysFragment : BaseMvpFragment(), HolidaysView {

    @InjectPresenter
    lateinit var holidaysPresenter: HolidaysPresenter

    private lateinit var holidayAdapter: HolidayAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var yearAdapter: YearAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun getLayoutId(): Int = R.layout.fragment_holidays

    override fun onViewCreated(view: View) {
        initListeners()
        setupHolidayAdapter()
        setupCountryAdapter()
        setupYearAdapter()
        holidaysPresenter.onCreate(
            holidayAdapter.holidayResponseItemClickObservable,
            countryAdapter.countryResponseItemClickObservable,
            yearAdapter.yearItemClickObservable
        )
    }

    companion object {
        fun newInstance(): HolidaysFragment = HolidaysFragment()
    }

    private fun initListeners() {
        vTvCountry.setOnClickListener {
            holidaysPresenter.onCountryTextClicked()
        }

        vTvYear.setOnClickListener {
            holidaysPresenter.onYearTextClicked()
        }
    }

    private fun setupHolidayAdapter() {
        holidayAdapter = HolidayAdapter()
        vRvHolidayList.adapter = holidayAdapter
    }

    private fun setupCountryAdapter() {
        countryAdapter = CountryAdapter()
        vRvCountryList.adapter = countryAdapter
    }


    private fun setupYearAdapter() {
        yearAdapter = YearAdapter()
        vRvYearList.adapter = yearAdapter
    }

    override fun updateHolidays(holidayResponseList: MutableList<HolidayResponse>) {
        holidayAdapter.setItems(holidayResponseList)
    }

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) {
        countryAdapter.setItems(countryResponseList)
    }

    override fun updateYears(yearList: MutableList<Int>) {
        yearAdapter.setItems(yearList)
    }

    override fun showCountryListBottomSheet() {
        vLlYearBottomSheet.hide()
        vLlBottomSheetView.show()
        bottomSheetBehavior = BottomSheetBehavior.from(vLlBottomSheetView)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun showYearListBottomSheet() {
        vLlBottomSheetView.hide()
        vLlYearBottomSheet.show()
        bottomSheetBehavior = BottomSheetBehavior.from(vLlYearBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun showCurrentCountryName(countryName: String?) {
        vTvCountry.text = countryName
    }

    override fun showCurrentYear() {
        vTvYear.text = holidaysPresenter.getYear().toString()
    }

    override fun updateHolidayStatusImage() {

    }
}