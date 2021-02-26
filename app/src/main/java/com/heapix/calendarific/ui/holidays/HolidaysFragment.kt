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
import com.insspring.poifox.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.fragment_holidays.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_year_picker.*

class HolidaysFragment : BaseMvpFragment(), HolidaysView {

    @InjectPresenter
    lateinit var holidaysPresenter: HolidaysPresenter

    private lateinit var holidayAdapter: HolidayAdapter
    private lateinit var countryAdapter: CountryAdapter
    private var yearAdapter: YearAdapter = YearAdapter()

    override fun getLayoutId(): Int = R.layout.fragment_holidays

    override fun onViewCreated(view: View) {
        initListeners()
        setupHolidayAdapter()
        setupCountryAdapter()
        setupOnTextChangedListener()
        holidaysPresenter.onCreate(
            holidayAdapter.holidayItemClickObservable,
            countryAdapter.countryItemClickObservable
        )
    }

    companion object {
        fun newInstance(): HolidaysFragment = HolidaysFragment()
    }

    private fun initListeners() {
        vTvCountry.setOnClickListener {
            holidaysPresenter.onCountryClicked()
        }

        vTvYear.setOnClickListener {
            holidaysPresenter.onYearClicked()
        }

        vFlBackButtonContainer.setOnClickListener {
            holidaysPresenter.onBackButtonClicked()
        }

        vNumberPicker.setOnScrollListener { _, _ ->
            holidaysPresenter.onNumberPickerScrolled(vNumberPicker.value)
        }
    }

    private fun setupHolidayAdapter() {
        holidayAdapter = HolidayAdapter()
        vRvHolidayList.adapter = holidayAdapter
    }

    private fun setupCountryAdapter() {
        countryAdapter = CountryAdapter()
        vRvCountriesList.adapter = countryAdapter
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

    override fun updateHolidays(holidayResponseList: MutableList<HolidayResponse>) {
        holidayAdapter.setItems(holidayResponseList)
    }

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) {
        countryAdapter.setItems(countryResponseList)
    }

    override fun updateYears(yearList: MutableList<Int>) {
        yearAdapter.setItems(yearList)
    }

    override fun showCountryList() {
        vBottomSheetCountryListHolidays.toggle()
    }

    override fun hideCountryList() {
        vBottomSheetCountryListHolidays.toggle()
    }

    override fun showYearPicker(year: Int) {
        vNumberPicker.minValue = 2000
        vNumberPicker.maxValue = 2049
        vNumberPicker.value = year
        vBottomSheetYearPickerHolidays.toggle()
    }

    override fun hideYearPicker() {
        vBottomSheetYearPickerHolidays.toggle()
    }

    override fun showChosenCountryName(countryName: String?) {
        vTvCountry.text = countryName
    }

    override fun showChosenYear(year: Int) {
        vTvYear.text = year.toString()
    }

    override fun hideKeyboard() {
        super.hideKeyboard(vEtSearch)
    }

}