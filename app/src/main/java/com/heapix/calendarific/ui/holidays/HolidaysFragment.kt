package com.heapix.calendarific.ui.holidays

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.BaseMvpFragment
import com.heapix.calendarific.ui.holidays.adapter.country.CountryAdapter
import com.heapix.calendarific.ui.holidays.adapter.holiday.HolidayAdapter
import com.heapix.calendarific.ui.holidays.adapter.year.YearAdapter
import com.heapix.calendarific.ui.navigation.NavigationActivity
import com.heapix.calendarific.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.fragment_holidays.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_year_list.*

class HolidaysFragment : BaseMvpFragment(), HolidaysView {

    @InjectPresenter
    lateinit var holidaysPresenter: HolidaysPresenter

    private lateinit var holidayAdapter: HolidayAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var yearAdapter: YearAdapter

    override fun getLayoutId(): Int = R.layout.fragment_holidays

    override fun onViewCreated(view: View) {
        initListeners()
        setupOnTextChangedListener()

        setupHolidayAdapter()
        setupCountryAdapter()
        setupYearAdapter()

        holidaysPresenter.onCreate(
            countryAdapter.countryItemClickObservable,
            yearAdapter.yearItemClickObservable
        )
    }

    companion object {
        fun newInstance(): HolidaysFragment = HolidaysFragment()

        private const val MIN_VALUE: Int = 2000
        private const val MAX_VALUE: Int = 2049
        private const val SIZE: Int = 50
        private const val FRICTION: Float = 0.02F
    }

    private fun initListeners() {
        vTvCountry.setOnClickListener {
            holidaysPresenter.onCountryClicked()
        }

        vTvYear.setOnClickListener {
            holidaysPresenter.onYearClicked()
        }

        vNumberPicker.setOnValueChangedListener { _, _, _ ->
            holidaysPresenter.onNumberPickerScrolled(vNumberPicker.value)
        }
    }

    private fun setupOnTextChangedListener() {
        vEtSearch.addTextChangedListener(
            object : SimpleTextWatcher() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    holidaysPresenter.onTextChanged(s.toString())
                }
            }
        )
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
    }

    override fun updateHolidays(holidayResponseList: MutableList<HolidayResponse>) =
        holidayAdapter.setItems(holidayResponseList)

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) =
        countryAdapter.setItems(countryResponseList)

    override fun updateYears(yearList: MutableList<Int>) =
        yearAdapter.setItems(yearList)

    override fun showCountryList() {
        if (vYearListHolidaysBottomSheet.isExpanded()) {
            vYearListHolidaysBottomSheet.toggle()
        } else {
            vCountryListHolidaysBottomSheet.updateBottomSheet {
                vEtSearch.clearFocus()
                vEtSearch.text.clear()

                vCountryListHolidaysBottomSheet.toggle()
            }
        }
    }

    override fun hideCountryList() = vCountryListHolidaysBottomSheet.toggle()

    override fun showYearList(year: Int) {
        if (vCountryListHolidaysBottomSheet.isExpanded()) {
            vCountryListHolidaysBottomSheet.toggle()
        }

        vNumberPicker.apply {
            displayedValues = Array(SIZE) { (it + MIN_VALUE).toString() }
            minValue = MIN_VALUE
            maxValue = MAX_VALUE
            value = year
            setFriction(FRICTION)
            wrapSelectorWheel = false
        }

        vYearListHolidaysBottomSheet.toggle()
    }

    override fun hideYearList() = vYearListHolidaysBottomSheet.toggle()

    override fun showChosenCountryName(countryName: String?) {
        vTvCountry.text = countryName
    }

    override fun showChosenYear(year: Int) {
        vTvYear.text = year.toString()
    }

    override fun onDoubleBackPressed() {
        (activity as? NavigationActivity)?.onDoubleBackPressed()
    }
}