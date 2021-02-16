package com.heapix.calendarific.ui.holidays

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryDetailsResponse
import com.heapix.calendarific.net.responses.holiday.HolidayDetailsResponse
import com.heapix.calendarific.ui.base.BaseMvpFragment
import com.heapix.calendarific.ui.holidays.adapter.CountriesAdapter
import com.heapix.calendarific.ui.holidays.adapter.HolidaysAdapter
import com.heapix.calendarific.ui.holidays.adapter.YearsAdapter
import com.heapix.calendarific.utils.view.hide
import com.heapix.calendarific.utils.view.show
import kotlinx.android.synthetic.main.fragment_holidays.*

class HolidaysFragment : BaseMvpFragment(), HolidaysView {

    @InjectPresenter
    lateinit var holidaysPresenter: HolidaysPresenter

    lateinit var holidaysAdapter: HolidaysAdapter
    lateinit var countriesAdapter: CountriesAdapter
    lateinit var yearsAdapter: YearsAdapter

    lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun getLayoutId(): Int = R.layout.fragment_holidays

    override fun onViewCreated(view: View) {
        initListeners()
        setupHolidaysAdapter()
        setupCountriesAdapter()
        setupYearsAdapter()
        holidaysPresenter.onCreate(
            holidaysAdapter.holidayItemClickObservable,
            countriesAdapter.countryItemClickObservable,
            yearsAdapter.yearItemClickObservable
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

    private fun setupHolidaysAdapter() {
        holidaysAdapter = HolidaysAdapter()
        vRvHolidayList.adapter = holidaysAdapter
    }

    private fun setupCountriesAdapter() {
        countriesAdapter = CountriesAdapter()
        vRvCountryList.adapter = countriesAdapter
    }

    private fun setupYearsAdapter() {
        yearsAdapter = YearsAdapter()
        vRvYearList.adapter = yearsAdapter
    }

    override fun updateHolidays(holidayDetailsResponseList: MutableList<HolidayDetailsResponse>) {
        holidaysAdapter.setItems(holidayDetailsResponseList)
    }

    override fun updateCountries(countryDetailsResponseList: MutableList<CountryDetailsResponse>) {
        countriesAdapter.setItems(countryDetailsResponseList)
    }

    override fun updateYears(yearList: MutableList<Int>) {
        yearsAdapter.setItems(yearList)
    }

    override fun showCountryListBottomSheet() {
        vLlYearBottomSheet.hide()
        vLlCountryBottomSheet.show()
        bottomSheetBehavior = BottomSheetBehavior.from(vLlCountryBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun showYearListBottomSheet() {
        vLlCountryBottomSheet.hide()
        vLlYearBottomSheet.show()
        bottomSheetBehavior = BottomSheetBehavior.from(vLlYearBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun showSelectCountryAndYear() {
        vTvSelectCountryAndYear.show()
    }

    override fun hideSelectCountryAndYear() {
        vTvSelectCountryAndYear.hide()
    }

}