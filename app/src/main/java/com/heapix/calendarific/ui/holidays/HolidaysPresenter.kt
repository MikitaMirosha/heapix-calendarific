package com.heapix.calendarific.ui.holidays

import com.arellomobile.mvp.InjectViewState
import com.heapix.calendarific.MyApp
import com.heapix.calendarific.model.Holidays
import com.heapix.calendarific.net.repo.HolidaysRepo
import com.heapix.calendarific.ui.base.BaseMvpPresenter
import io.reactivex.Observable
import org.kodein.di.instance

@InjectViewState
class HolidaysPresenter : BaseMvpPresenter<HolidaysView>() {

    private val holidaysRepo: HolidaysRepo by MyApp.kodein.instance()
    private lateinit var holidaysModelList: MutableList<Holidays>

    fun onCreate(holidaysItemClickObservable: Observable<Holidays>) {
        getHolidaysAndUpdateUi()
//        setupOnHolidaysItemClickListener(holidaysItemClickObservable)
    }

    private fun getHolidaysAndUpdateUi() {
        addDisposable(
            holidaysRepo.getAllHolidays()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe(
                    {
                        holidaysModelList = it
                        viewState.updateHolidays(it)
                    }, {

                    }
                )
        )
    }

//    private fun setupOnHolidaysItemClickListener(holidaysItemClickObservable: Observable<Holidays>) {
//        addDisposable(
//            holidaysItemClickObservable
//                .subscribeOn(schedulers.io())
//                .observeOn(schedulers.ui())
//                .subscribe(
//                    {
//
//                    }, {
//
//                    }
//                )
//        )
//    }

}