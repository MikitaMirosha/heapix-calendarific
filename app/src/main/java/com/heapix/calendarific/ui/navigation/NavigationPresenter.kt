package com.heapix.calendarific.ui.navigation

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.net.repo.CountryRepo
import com.heapix.calendarific.net.repo.YearRepo
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class NavigationPresenter : BaseMvpPresenter<NavigationView>() {

    private val countryRepo: CountryRepo by MyApp.kodein.instance()
    private val yearRepo: YearRepo by MyApp.kodein.instance()

    private lateinit var countryResponseList: MutableList<CountryResponse>

    fun onCreate(
        countryResponseItemClickObservable: Observable<CountryResponse>
    ) {
        checkIsoAndYearInSharedPreferences()
        getCountriesAndUpdateUi()
        setupOnCountryResponseItemClickListener(countryResponseItemClickObservable)
    }

    private fun checkIsoAndYearInSharedPreferences() {
        if (!isIsoInSharedPreferences() && !isYearInSharedPreferences()) {
            viewState.showSelectionView()
        }
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

    private fun setupOnCountryResponseItemClickListener(countryResponseItemClickObservable: Observable<CountryResponse>) {
        addDisposable(
            countryResponseItemClickObservable
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        saveIso(it.iso)
                        showMessage("Selected country is " + it.iso)
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

    private fun getIso(): String? = countryRepo.getIso()

    fun getYear(): Int = yearRepo.getYear()

    private fun saveIso(iso: String?) = countryRepo.saveIso(iso)

    private fun isIsoInSharedPreferences(): Boolean = getIso() != ""

    private fun isYearInSharedPreferences(): Boolean = getYear() != 0

    fun onSelectCountryClicked() = viewState.showCountryList()

    fun onSelectYearClicked() = viewState.showYearPicker()

    fun onNextButtonClicked() {
        if (!isIsoInSharedPreferences() && !isYearInSharedPreferences()) {
            viewState.showHolidaysFragment()
        } else {
            showMessage("Please, enter full data")
        }
    }

    fun onBackButtonClicked() = viewState.hideYearPicker()

    fun getNumberPickerValue() = viewState.onNumberPickerValueChanged()

}