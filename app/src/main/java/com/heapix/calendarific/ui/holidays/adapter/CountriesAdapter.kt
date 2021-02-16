package com.heapix.calendarific.ui.holidays.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryDetailsResponse
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CountriesAdapter : BaseListAdapter<CountryDetailsResponse>() {

    private val countryPublishSubject: PublishSubject<CountryDetailsResponse> =
        PublishSubject.create()
    val countryItemClickObservable: Observable<CountryDetailsResponse> = countryPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryDetailsResponse> {
        return CountriesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday_parameter, parent, false),
            countryPublishSubject
        )
    }

}