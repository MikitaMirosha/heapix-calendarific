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

    fun onCreate(
        countryItemClickObservable: Observable<CountryResponse>,
        yearItemClickObservable: Observable<Int>
    ) {
        checkIsoAndYearInStorage()
        setCurrentYearInStorage()
        getCountriesAndUpdateUi()
        getYearsAndUpdateUi()
        setupOnCountryItemClickListener(countryItemClickObservable)
        setupOnYearItemClickListener(yearItemClickObservable)
    }

    private fun checkIsoAndYearInStorage() {
        if (countryRepo.isIsoInStorage() && yearRepo.isYearInStorage()) {
            viewState.openNavigationActivity()
        }
    }

    private fun setCurrentYearInStorage() {
        yearRepo.saveYear(LocalDate().yearOfEra)
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

    private fun setupOnCountryItemClickListener(countryResponseItemClickObservable: Observable<CountryResponse>) {
        addDisposable(
            countryResponseItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        countryRepo.saveIso(it.iso)
                        onCountryItemClicked()
                        viewState.showChosenCountryName(it.countryName)
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
                        viewState.toggleBottomSheet()
                        viewState.showChosenYear(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    private fun onCountryItemClicked() {
        viewState.hideCountryList()
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

    fun onSelectYearClicked() = viewState.toggleBottomSheet()

    fun onNextButtonClicked() {
        if (countryRepo.isIsoInStorage() && yearRepo.isYearInStorage()) {
            viewState.openNavigationActivity()
        } else {
            showMessage(R.string.please_enter_full_data)
        }
    }

}