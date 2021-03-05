package com.heapix.calendarific.ui.holidays

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.net.repo.CountryRepo
import com.heapix.calendarific.net.repo.HolidayRepo
import com.heapix.calendarific.net.repo.YearRepo
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import com.heapix.calendarific.utils.extentions.findCountryNameByIso
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class HolidaysPresenter : BaseMvpPresenter<HolidaysView>() {

    private val holidayRepo: HolidayRepo by MyApp.kodein.instance()
    private val countryRepo: CountryRepo by MyApp.kodein.instance()
    private val yearRepo: YearRepo by MyApp.kodein.instance()

    private lateinit var countryResponseList: MutableList<CountryResponse>

    fun onCreate(
        countryItemClickObservable: Observable<CountryResponse>,
        yearItemClickObservable: Observable<Int>
    ) {
        getHolidaysAndUpdateUi()
        getCountriesAndUpdateUi()
        getYearsAndUpdateUi()

        setupOnCountryItemClickListener(countryItemClickObservable)
        setupOnYearItemClickListener(yearItemClickObservable)
    }

    private fun getHolidaysAndUpdateUi() {
        addDisposable(
            holidayRepo.getAllHolidays(countryRepo.getIso(), yearRepo.getYear())
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
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

                        showChosenCountryName(it)
                        viewState.updateCountries(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun getYearsAndUpdateUi() {
        showChosenYear(yearRepo.getYear())
        viewState.updateYears(yearRepo.getAllYears())
    }

    private fun setupOnCountryItemClickListener(countryItemClickObservable: Observable<CountryResponse>) {
        addDisposable(
            countryItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        countryRepo.saveIso(it.iso)

                        getHolidaysAndUpdateUi()
                        setChosenCountry(it.countryName)
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
                        yearRepo.saveYear(it)

                        viewState.showChosenYear(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun showChosenCountryName(countryName: MutableList<CountryResponse>) =
        viewState.showChosenCountryName(
            findCountryNameByIso(
                countryName,
                countryRepo.getIso()
            )
        )

    private fun showChosenYear(year: Int) = viewState.showChosenYear(year)

    private fun setChosenCountry(countryName: String?) {
        viewState.hideCountryList()
        viewState.showChosenCountryName(countryName)
    }

    fun onTextChanged(text: String) {
        viewState.updateCountries(
            countryResponseList.filter {
                it.countryName?.contains(
                    other = text,
                    ignoreCase = true
                ) ?: true
            }.toMutableList()
        )
    }

    fun onCountryClicked() = viewState.showCountryList()

    fun onYearClicked() = viewState.showYearList(yearRepo.getYear())

    fun onNumberPickerScrolled(year: Int) {
        yearRepo.saveYear(year)

        getHolidaysAndUpdateUi()
        viewState.showChosenYear(yearRepo.getYear())
    }

}