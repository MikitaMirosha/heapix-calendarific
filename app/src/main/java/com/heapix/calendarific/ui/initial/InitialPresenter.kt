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
        countryItemClickObservable: Observable<CountryResponse>
    ) {
        setCurrentYearInSharedPreferences()
        checkIsoAndYearInSharedPreferences()
        getCountriesAndUpdateUi()
        setupOnCountryItemClickListener(countryItemClickObservable)
    }

    private fun checkIsoAndYearInSharedPreferences() {
        if (isIsoInSharedPreferences() && isYearInSharedPreferences()) {
            viewState.openNavigationActivity()
        }
    }

    private fun setCurrentYearInSharedPreferences() {
        countryRepo.saveYear(LocalDate().yearOfEra)
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

    private fun setupOnCountryItemClickListener(countryResponseItemClickObservable: Observable<CountryResponse>) {
        addDisposable(
            countryResponseItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        countryRepo.saveIso(it.iso)
                        countryRepo.saveCountryName(it.countryName)
                        viewState.hideKeyboard()
                        viewState.hideCountryList()
                        viewState.showChosenCountryName(it.countryName)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

    fun onTextChanged(text: String) {
        viewState.updateCountries(
            countryResponseList.filter {
                it.countryName?.contains(
                    text,
                    ignoreCase = true
                ) ?: true
            }.toMutableList()
        )
    }

    private fun getIso(): String = countryRepo.getIso()

    private fun getYear(): Int = yearRepo.getYear()

    private fun isIsoInSharedPreferences(): Boolean = getIso() != ""

    private fun isYearInSharedPreferences(): Boolean = getYear() != 0

    fun onSelectCountryClicked() = viewState.showCountryList()

    fun onSelectYearClicked() = viewState.showYearPicker(getYear())

    fun onNextButtonClicked() {
        if (isIsoInSharedPreferences() && isYearInSharedPreferences()) {
            viewState.openNavigationActivity()
        } else {
            showMessage(R.string.please_enter_full_data)
        }
    }

    fun onBackButtonClicked() = viewState.hideYearPicker()

    fun onNumberPickerScrolled(year: Int) {
        countryRepo.saveYear(year)
        viewState.showChosenYear(getYear())
    }
}