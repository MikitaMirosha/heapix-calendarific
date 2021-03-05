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
import com.heapix.calendarific.ui.navigation.NavigationActivity
import com.heapix.calendarific.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_year_list.*

class InitialActivity : BaseMvpActivity(), InitialView {

    @InjectPresenter
    lateinit var initialPresenter: InitialPresenter

    private var isOnDoubleBackPressed = false

    private lateinit var countryAdapter: CountryAdapter

    override fun getLayoutId(): Int = R.layout.activity_initial

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()
        setupCountryAdapter()

        initialPresenter.onCreate(countryAdapter.countryItemClickObservable)
    }

    companion object {
        private const val DELAY_MILLIS: Long = 2000
        private const val MIN_VALUE: Int = 2000
        private const val MAX_VALUE: Int = 2049
        private const val SIZE: Int = 50
        private const val FRICTION: Float = 0.02F
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

        vNumberPicker.setOnValueChangedListener { _, _, _ ->
            initialPresenter.onNumberPickerScrolled(vNumberPicker.value)
        }

        setupOnTextChangedListener()
    }

    private fun setupCountryAdapter() {
        countryAdapter = CountryAdapter()
        vRvCountryList.adapter = countryAdapter
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
            DELAY_MILLIS
        )
    }

    override fun onBackPressed() {
        if (vCountryListBottomSheet.isExpanded()) {
            vEtSearch.text.clear()
            vEtSearch.clearFocus()

            vCountryListBottomSheet.toggle()
        } else if (vYearListBottomSheet.isExpanded()) {
            vYearListBottomSheet.toggle()
        } else {
            onDoubleBackPressed()
        }
    }

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) {
        countryAdapter.setItems(countryResponseList)
    }

    override fun showCountryList() {
        vCountryListBottomSheet.updateBottomSheet {
            vEtSearch.clearFocus()
            vEtSearch.text.clear()

            vCountryListBottomSheet.toggle()
        }
    }

    override fun hideCountryList() = vCountryListBottomSheet.toggle()

    override fun showYearList(year: Int) {
        vNumberPicker.apply {
            displayedValues = Array(SIZE) { (it + MIN_VALUE).toString() }
            minValue = MIN_VALUE
            maxValue = MAX_VALUE
            value = year
            setFriction(FRICTION)
            wrapSelectorWheel = false
        }

        vYearListBottomSheet.toggle()
    }

    override fun hideYearList() = vYearListBottomSheet.toggle()

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
}