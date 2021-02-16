package com.heapix.calendarific.ui.holidays.adapter

import android.view.View
import com.heapix.calendarific.net.responses.country.CountryDetailsResponse
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday_parameter.*

class CountriesViewHolder(
    itemView: View,
    private val countryPublishSubject: PublishSubject<CountryDetailsResponse>
) : BaseViewHolder<CountryDetailsResponse>(itemView) {

    override fun bind(model: CountryDetailsResponse) {
        setupCountryName(model)
        setupClickListener(model)
    }

    private fun setupCountryName(countryDetailsResponse: CountryDetailsResponse) {
        vTvParameterName.text = countryDetailsResponse.country_name
    }

    private fun setupClickListener(countryDetailsResponse: CountryDetailsResponse) {
        setOnClickListener {
            countryPublishSubject.onNext(countryDetailsResponse)
        }
    }

}