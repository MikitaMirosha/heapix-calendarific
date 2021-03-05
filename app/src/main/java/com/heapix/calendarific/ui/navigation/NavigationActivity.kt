package com.heapix.calendarific.ui.navigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentTransaction
import com.heapix.calendarific.R
import com.heapix.calendarific.ui.base.BaseMvpActivity
import com.heapix.calendarific.ui.calendar.CalendarFragment
import com.heapix.calendarific.ui.holidays.HolidaysFragment
import com.heapix.calendarific.ui.world.WorldFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_holidays.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_country_list.view.*
import kotlinx.android.synthetic.main.view_year_list.*

class NavigationActivity : BaseMvpActivity() {

    private var isOnDoubleBackPressed = false

    override fun getLayoutId() = R.layout.activity_navigation

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setupNavigation(savedInstanceState)
        setupNavigationListeners()
    }

    companion object {
        private const val DELAY_MILLIS: Long = 2000
    }

    private var fragNavController: FragNavController =
        FragNavController(supportFragmentManager, R.id.vFlNavigationContainer)

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

    fun onDoubleBackPressed() {
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
        if (vCountryListHolidaysBottomSheet.isExpanded()) {
            vEtSearch.text.clear()
            vEtSearch.clearFocus()
            vCountryListHolidaysBottomSheet.toggle()
        } else if (vYearListHolidaysBottomSheet.isExpanded()) {
            vYearListHolidaysBottomSheet.toggle()
        } else {
            onDoubleBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

}