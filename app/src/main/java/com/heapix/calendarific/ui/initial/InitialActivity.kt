package com.heapix.calendarific.ui.initial

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpActivity
import com.heapix.calendarific.ui.holidays.adapter.country.CountryAdapter
import com.heapix.calendarific.ui.navigation.NavigationActivity
import com.insspring.poifox.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_year_picker.*

class InitialActivity : BaseMvpActivity(), InitialView {

    @InjectPresenter
    lateinit var initialPresenter: InitialPresenter

    private lateinit var countryAdapter: CountryAdapter

    override fun getLayoutId(): Int = R.layout.activity_initial

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()
        setupCountryAdapter()
        initialPresenter.onCreate(countryAdapter.countryItemClickObservable)
    }

    private fun initListeners() {
        vTvCountryName.setOnClickListener {
            initialPresenter.onSelectCountryClicked()
        }

        vTvYearNumber.setOnClickListener {
            initialPresenter.onSelectYearClicked()
        }

        vTvNext.setOnClickListener {
            initialPresenter.onNextButtonClicked()
        }

        vFlBackButtonContainer.setOnClickListener {
            initialPresenter.onBackButtonClicked()
        }

        vNumberPicker.setOnValueChangedListener { _, _, _ ->
            initialPresenter.onNumberPickerScrolled(vNumberPicker.value)
        }

        setupOnTextChangedListener()
    }

    private fun setupCountryAdapter() {
        countryAdapter = CountryAdapter()
        vRvCountriesList.adapter = countryAdapter
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

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) {
        countryAdapter.setItems(countryResponseList)
    }

    override fun showCountryList() {
        vCountryListBottomSheet.toggle()
    }

    override fun hideCountryList() {
        vCountryListBottomSheet.toggle()
    }

    override fun showYearPicker(year: Int) {
        vNumberPicker.minValue = 2000
        vNumberPicker.maxValue = 2049
        vNumberPicker.value = year
        vYearPickerBottomSheet.toggle()
    }

    override fun hideYearPicker() {
        vYearPickerBottomSheet.toggle()
    }

    override fun showChosenCountryName(countryName: String?) {
        vTvCountryName.text = countryName
    }

    override fun showChosenYear(year: Int) {
        vTvYearNumber.text = year.toString()
    }

    override fun openNavigationActivity() {
        finish()
        val intent = Intent(this, NavigationActivity::class.java)
        intent.putExtra("COUNTRY_NAME", true)
        startActivity(intent)
    }

    override fun hideKeyboard() {
        super.hideKeyboard(vEtSearch)
    }

}