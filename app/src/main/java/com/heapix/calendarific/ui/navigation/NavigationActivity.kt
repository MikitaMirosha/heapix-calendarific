package com.heapix.calendarific.ui.navigation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.heapix.calendarific.R
import com.heapix.calendarific.ui.base.BaseMvpActivity
import com.heapix.calendarific.ui.calendar.CalendarFragment
import com.heapix.calendarific.ui.holidays.HolidaysFragment
import com.heapix.calendarific.ui.world.WorldFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.fragment_holidays.*
import kotlinx.android.synthetic.main.view_country_list.*
import kotlinx.android.synthetic.main.view_year_picker.*

class NavigationActivity : BaseMvpActivity(), NavigationView {

    @InjectPresenter
    lateinit var navigationPresenter: NavigationPresenter

    private var isOnDoubleBackPressed = false

    override fun getLayoutId() = R.layout.activity_navigation

    override fun onCreateActivity(savedInstanceState: Bundle?) {
        setupNavigation(savedInstanceState)
        setupNavigationListeners()
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

}