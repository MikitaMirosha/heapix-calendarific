package com.heapix.calendarific.ui.holidays

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.model.HolidayModel
import com.heapix.calendarific.net.repo.HolidaysRepo
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class HolidaysPresenter : BaseMvpPresenter<HolidaysView>() {

    private val holidaysRepo: HolidaysRepo by MyApp.kodein.instance()
    private lateinit var holidayModelList: MutableList<HolidayModel>

    fun onCreate(holidayItemClickObservable: Observable<HolidayModel>) {
        getHolidaysAndUpdateUi()
    }

    private fun getHolidaysAndUpdateUi() {
        addDisposable(
            holidaysRepo.getAllHolidays()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        holidayModelList = it
                        viewState.updateHolidays(it)
                    }, {
                        Log.e("TAG", it.toString())
                    }
                )
        )
    }

}