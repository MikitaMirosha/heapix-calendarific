package com.heapix.calendarific.ui.initial

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.R
import com.heapix.calendarific.net.repo.CountryRepo
import com.heapix.calendarific.net.repo.YearRepo
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import org.joda.time.LocalDate
import org.kodein.di.instance

@InjectViewState
class InitialPresenter : BaseMvpPresenter<InitialView>() {

    private val countryRepo: CountryRepo by MyApp.kodein.instance()
    private val yearRepo: YearRepo by MyApp.kodein.instance()

    private lateinit var countryResponseList: MutableList<CountryResponse>

    fun onCreate(countryItemClickObservable: Observable<CountryResponse>) {
        checkIsoAndYearInStorage()
        setCurrentYearInStorage()

        getCountriesAndUpdateUi()

        setupOnCountryItemClickListener(countryItemClickObservable)
    }

    private fun checkIsoAndYearInStorage() {
        if (areIsoAndYearInStorage()) {
            viewState.openNavigationActivity()
        }
    }

    private fun setCurrentYearInStorage() {
        yearRepo.saveYear(LocalDate().yearOfEra)
        viewState.showChosenYear(LocalDate().yearOfEra)
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

    private fun setupOnCountryItemClickListener(countryItemClickObservable: Observable<CountryResponse>) {
        addDisposable(
            countryItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        countryRepo.saveIso(it.iso)

                        setChosenCountry(it.countryName)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun areIsoAndYearInStorage(): Boolean =
        countryRepo.isIsoInStorage() && yearRepo.isYearInStorage()

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

    fun onSelectCountryClicked() = viewState.showCountryList()

    fun onSelectYearClicked() = viewState.showYearList(yearRepo.getYear())

    fun onNumberPickerScrolled(year: Int) {
        yearRepo.saveYear(year)
        viewState.showChosenYear(yearRepo.getYear())
    }

    fun onNextButtonClicked() {
        if (areIsoAndYearInStorage()) {
            viewState.openNavigationActivity()
        } else {
            showMessage(R.string.please_enter_full_data)
        }
    }

}