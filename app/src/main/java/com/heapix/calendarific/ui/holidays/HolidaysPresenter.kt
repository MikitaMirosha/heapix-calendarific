package com.heapix.calendarific.ui.holidays

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.net.repo.CountryRepo
import com.heapix.calendarific.net.repo.HolidayRepo
import com.heapix.calendarific.net.repo.YearRepo
import com.heapix.calendarific.net.responses.country.CountryDetailsResponse
import com.heapix.calendarific.net.responses.holiday.HolidayDetailsResponse
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class HolidaysPresenter : BaseMvpPresenter<HolidaysView>() {

    private val holidayRepo: HolidayRepo by MyApp.kodein.instance()
    private val countryRepo: CountryRepo by MyApp.kodein.instance()
    private val yearRepo: YearRepo by MyApp.kodein.instance()

    fun onCreate(
        holidayItemClickObservable: Observable<HolidayDetailsResponse>,
        countryItemClickObservable: Observable<CountryDetailsResponse>,
        yearItemClickListener: Observable<Int>
    ) {
        checkCountryAndYearInSharedPreferences()
        getHolidaysAndUpdateUi(
            getCountryFromSharedPreferences() ?: "",
            getYearFromSharedPreferences()
        )
        getCountriesAndUpdateUi()
        getYearsAndUpdateUi()
        setupOnHolidayItemClickListener(holidayItemClickObservable)
        setupOnCountryItemClickListener(countryItemClickObservable)
        setupOnYearItemClickListener(yearItemClickListener)
    }

    private fun getCountryFromSharedPreferences(): String? =
        countryRepo.getCountryFromSharedPreferences()

    private fun saveCountryToSharedPreferences(country: String?) =
        countryRepo.saveCountryToSharedPreferences(country)

    private fun getYearFromSharedPreferences(): Int =
        yearRepo.getYearFromSharedPreferences()

    private fun saveYearToSharedPreferences(year: Int) =
        yearRepo.saveYearToSharedPreferences(year)

    private fun isCountryInSharedPreferences(): Boolean = getCountryFromSharedPreferences() != ""

    private fun isYearInSharedPreferences(): Boolean = getYearFromSharedPreferences() != 0

    private fun checkCountryAndYearInSharedPreferences() {
        if (!isCountryInSharedPreferences() && !isYearInSharedPreferences()) {
            viewState.showSelectCountryAndYear()
        }
    }

    private fun getHolidaysAndUpdateUi(iso: String, year: Int) {
        addDisposable(
            holidayRepo.getAllHolidays(iso, year)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        viewState.hideSelectCountryAndYear()
                        viewState.updateHolidays(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getCountriesAndUpdateUi() {
        addDisposable(
            countryRepo.getAllCountries()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        viewState.updateCountries(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getYearsAndUpdateUi() {
        viewState.updateYears(yearRepo.getAllYears())
    }

    private fun setupOnHolidayItemClickListener(holidayItemClickObservable: Observable<HolidayDetailsResponse>) {
        addDisposable(
            holidayItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        showMessage("Holiday card is pressed")
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupOnCountryItemClickListener(countryItemClickObservable: Observable<CountryDetailsResponse>) {
        addDisposable(
            countryItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        getHolidaysAndUpdateUi(it.iso ?: "", getYearFromSharedPreferences())
                        saveCountryToSharedPreferences(it.iso)
                        showMessage("Selected country is " + it.iso)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun setupOnYearItemClickListener(yearItemClickObservable: Observable<Int>) {
        addDisposable(
            yearItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        getHolidaysAndUpdateUi(getCountryFromSharedPreferences() ?: "", it)
                        saveYearToSharedPreferences(it)
                        showMessage("Selected year is  $it")
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    fun onCountryTextClicked() = viewState.showCountryListBottomSheet()

    fun onYearTextClicked() = viewState.showYearListBottomSheet()

}