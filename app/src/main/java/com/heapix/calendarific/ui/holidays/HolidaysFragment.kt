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
import com.insspring.poifox.utils.SimpleTextWatcher
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
        setupHolidayAdapter()
        setupCountryAdapter()
        setupYearAdapter()
        setupOnTextChangedListener()
        holidaysPresenter.onCreate(
            countryAdapter.countryItemClickObservable,
            yearAdapter.yearItemClickObservable
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
        vCountryListHolidaysBottomSheet.toggle()
    }

    override fun hideCountryList() {
        hideKeyboard(vEtSearch)
        vCountryListHolidaysBottomSheet.toggle()
    }

    override fun showChosenCountryName(countryName: String?) {
        vTvCountry.text = countryName
    }

    override fun showChosenYear(year: Int) {
        vTvYear.text = year.toString()
    }

    override fun toggleBottomSheet() = vYearListHolidaysBottomSheet.toggle()

    override fun onDoubleBackPressed() {
        (activity as? NavigationActivity)?.onDoubleBackPressed()
    }
}