package com.heapix.calendarific.ui.holidays

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.net.repo.CountryRepo
import com.heapix.calendarific.net.repo.HolidayRepo
import com.heapix.calendarific.net.repo.YearRepo
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.net.responses.holiday.HolidayResponse
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import org.joda.time.LocalDate
import org.kodein.di.instance

@InjectViewState
class HolidaysPresenter : BaseMvpPresenter<HolidaysView>() {

    private val holidayRepo: HolidayRepo by MyApp.kodein.instance()
    private val countryRepo: CountryRepo by MyApp.kodein.instance()
    private val yearRepo: YearRepo by MyApp.kodein.instance()

    private lateinit var countryResponseList: MutableList<CountryResponse>

    fun onCreate(
        holidayResponseItemClickObservable: Observable<HolidayResponse>,
        countryResponseItemClickObservable: Observable<CountryResponse>,
        yearItemClickObservable: Observable<Int>
    ) {
        getHolidaysAndUpdateUi()
        getCountriesAndUpdateUi()
        getYearsAndUpdateUi()
        setupOnHolidayResponseItemClickListener(holidayResponseItemClickObservable)
        setupOnCountryResponseItemClickListener(countryResponseItemClickObservable)
        setupOnYearItemClickListener(yearItemClickObservable)
    }

    private fun getHolidaysAndUpdateUi() {
        addDisposable(
            holidayRepo.getAllHolidays(getIso() ?: "", getYear())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
//                        viewState.hideSelectCountryAndYear()
                        viewState.updateHolidayStatusImage()
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
                        countryResponseList = it
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

    private fun setupOnHolidayResponseItemClickListener(holidayResponseItemClickObservable: Observable<HolidayResponse>) {
        addDisposable(
            holidayResponseItemClickObservable
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

    private fun setupOnCountryResponseItemClickListener(countryResponseItemClickObservable: Observable<CountryResponse>) {
        addDisposable(
            countryResponseItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        getHolidaysAndUpdateUi()
                        saveIso(it.iso)
                        viewState.showCurrentCountryName(it.countryName)
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
                        getHolidaysAndUpdateUi()
                        saveYear(it)
                        viewState.showCurrentYear()
                        showMessage("Selected year is $it")
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getIso(): String? = countryRepo.getIso()

    fun getYear(): Int = yearRepo.getYear()

    private fun saveIso(iso: String?) = countryRepo.saveIso(iso)

    private fun saveYear(year: Int) = yearRepo.saveYear(year)

    fun onCountryTextClicked() = viewState.showCountryListBottomSheet()

    fun onYearTextClicked() = viewState.showYearListBottomSheet()

    private fun getLocalDate() = LocalDate()

//    private fun isHolidayPassed(): Boolean {
//        holidayRepo.getHolidayDate()
//    }

}