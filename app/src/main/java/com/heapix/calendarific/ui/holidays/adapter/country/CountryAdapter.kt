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

    private val countryPublishSubject: PublishSubject<CountryResponse> =
        PublishSubject.create()
    val countryItemClickObservable: Observable<CountryResponse> =
        countryPublishSubject

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CountryResponse> {
        return CountryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country_name, parent, false),
            countryPublishSubject
        )
    }

}