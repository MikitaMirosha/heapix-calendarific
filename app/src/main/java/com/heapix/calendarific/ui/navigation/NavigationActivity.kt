package com.heapix.calendarific.ui.navigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpActivity
import com.heapix.calendarific.ui.calendar.CalendarFragment
import com.heapix.calendarific.ui.holidays.HolidaysFragment
import com.heapix.calendarific.ui.holidays.adapter.country.CountryAdapter
import com.heapix.calendarific.ui.world.WorldFragment
import com.heapix.calendarific.utils.view.hide
import com.heapix.calendarific.utils.view.show
import com.insspring.poifox.utils.SimpleTextWatcher
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_holidays.*
import kotlinx.android.synthetic.main.view_holiday_parameters.*
import kotlinx.android.synthetic.main.view_number_picker.*
import kotlinx.android.synthetic.main.view_selection.*
import kotlinx.android.synthetic.main.view_selection.view.*

class NavigationActivity : BaseMvpActivity(), NavigationView {

    @InjectPresenter
    lateinit var navigationPresenter: NavigationPresenter

    private lateinit var countryAdapter: CountryAdapter

    private var isOnDoubleBackPressed = false

    override fun getLayoutId() = R.layout.activity_navigation

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        initListeners()
        setupCountryAdapter()
        setupNavigation(savedInstanceState)
        setupNavigationListeners()
        navigationPresenter.onCreate(countryAdapter.countryResponseItemClickObservable)
    }

    private fun initListeners() {
        vFlSelectCountry.setOnClickListener {
            navigationPresenter.onSelectCountryClicked()
        }

        vFlSelectYear.setOnClickListener {
            navigationPresenter.onSelectYearClicked()
        }

        vTvNext.setOnClickListener {
            navigationPresenter.onNextButtonClicked()
        }

        vIvBack.setOnClickListener {
            navigationPresenter.onBackButtonClicked()
        }

        vNumberPicker.setOnValueChangedListener { _, _, _ ->
            navigationPresenter.getNumberPickerValue()
        }

        setupOnTextChangedListener()
    }

    private fun setupCountryAdapter() {
        countryAdapter = CountryAdapter()
        vRvParameterList.adapter = countryAdapter
    }

    private var fragNavController: FragNavController =
        FragNavController(supportFragmentManager, R.id.vFlContainer)

    private val fragmentsList = listOf(
        HolidaysFragment.newInstance(),
        CalendarFragment.newInstance(),
        WorldFragment.newInstance()
    )

    private fun setupNavigation(savedInstanceState: Bundle?) {
        fragNavController.fragmentHideStrategy = FragNavController.HIDE
        fragNavController.rootFragments = fragmentsList
        fragNavController.defaultTransactionOptions =
            FragNavTransactionOptions.newBuilder().customAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right
            )
                .transition(FragmentTransaction.TRANSIT_NONE)
                .build()

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    private fun setupNavigationListeners() {
        vBottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homePage -> {
                    fragNavController.switchTab(FragNavController.TAB1)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.calendarPage -> {
                    fragNavController.switchTab(FragNavController.TAB2)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.worldPage -> {
                    fragNavController.switchTab(FragNavController.TAB3)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun setupOnTextChangedListener() {
        vEtSearch.addTextChangedListener(
            object : SimpleTextWatcher() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    navigationPresenter.onTextChanged(s.toString())
                }
            }
        )
    }

    fun navigateToFragment(fragment: Fragment) = fragNavController.pushFragment(fragment)

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
        if (fragNavController.isRootFragment) {
            onDoubleBackPressed()
        } else {
            fragNavController.popFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

    override fun updateCountries(countryResponseList: MutableList<CountryResponse>) {
        countryAdapter.setItems(countryResponseList)
    }

    override fun showSelectionView() {
        vSelectionView.show()
    }

    override fun showCountryList() {
        vTvParameterNameBottomSheet.text = getString(R.string.country)
        vYearList.hide()
        vCountryList.show()
        vBottomSheet.toggle()
    }

    override fun hideCountryList() {
        vBottomSheet.hide()
    }

    override fun showYearPicker() {
        vNumberPicker.minValue = 2000
        vNumberPicker.maxValue = 2049
        vCountryList.hide()
        vYearList.show()
        vBottomSheet.toggle()
    }

    override fun hideYearPicker() {
        vYearList.hide()
    }

    override fun showHolidaysFragment() {
        vSelectionView.hide()
    }

    override fun onNumberPickerValueChanged() {
        vTvYear.text = vNumberPicker.value.toString()
    }

}