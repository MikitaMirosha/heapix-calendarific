package com.heapix.calendarific.ui.initial

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpActivity
import com.heapix.calendarific.ui.holidays.adapter.country.CountryAdapter
import com.heapix.calendarific.ui.holidays.adapter.year.YearAdapter
import com.heapix.calendarific.ui.navigation.NavigationActivity
import com.insspring.poifox.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_year_list.*

class InitialActivity : BaseMvpActivity(), InitialView {

    @InjectPresenter
    lateinit var initialPresenter: InitialPresenter

    private var isOnDoubleBackPressed = false

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var yearAdapter: YearAdapter

    override fun getLayoutId(): Int = R.layout.activity_initial

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()
        setupCountryAdapter()
        setupYearAdapter()
        initialPresenter.onCreate(
            countryAdapter.countryItemClickObservable,
            yearAdapter.yearItemClickObservable
        )
    }

    private fun initListeners() {
        vLlSelectCountry.setOnClickListener {
            initialPresenter.onSelectCountryClicked()
        }

        vLlSelectYear.setOnClickListener {
            initialPresenter.onSelectYearClicked()
        }

        vTvNext.setOnClickListener {
            initialPresenter.onNextButtonClicked()
        }

        setupOnTextChangedListener()
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
                    initialPresenter.onTextChanged(s.toString())
                }
            }
        )
    }

    private fun onDoubleBackPressed() {
        if (isOnDoubleBackPressed) {
            super.onBackPressed()
            return
        }

        this.isOnDoubleBackPressed = true
        showMessage(R.string.press_back_again_to_exit)

        Handler(Looper.getMainLooper()).postDelayed(
            { isOnDoubleBackPressed = false },
            2000
        )
    }

    override fun onBackPressed() {
        if (vCountryListBottomSheet.isExpanded()) {
            vEtSearch.text.clear()
            vCountryListBottomSheet.toggle()
        } else {
            onDoubleBackPressed()
        }
    }

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) {
        countryAdapter.setItems(countryResponseList)
    }

    override fun updateYears(yearList: MutableList<Int>) {
        yearAdapter.setItems(yearList)
    }

    override fun showCountryList() {
        vCountryListBottomSheet.toggle()
    }

    override fun hideCountryList() {
        hideKeyboard(vEtSearch)
        vCountryListBottomSheet.toggle()
    }

    override fun showChosenCountryName(countryName: String?) {
        vTvSelectInitialCountry.text = countryName
    }

    override fun showChosenYear(year: Int) {
        vTvSelectInitialYear.text = year.toString()
    }

    override fun openNavigationActivity() {
        finish()
        startActivity(Intent(this, NavigationActivity::class.java))
    }

    override fun toggleBottomSheet() = vYearListBottomSheet.toggle()

}