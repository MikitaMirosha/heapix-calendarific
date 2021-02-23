package com.heapix.calendarific.ui.holidays.adapter.country

import android.view.LayoutInflater
import android.view.ViewGroup
import com.heapix.calendarific.R
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.adapters.BaseListAdapter
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CountryAdapter : BaseListAdapter<CountryResponse>() {

    private val countryResponsePublishSubject: PublishSubject<CountryResponse> =
        PublishSubject.create()
    val countryResponseItemClickObservable: Observable<CountryResponse> =
        countryResponsePublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryResponse> {
        return CountryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_holiday_parameter, parent, false),
            countryResponsePublishSubject
        )
    }

}