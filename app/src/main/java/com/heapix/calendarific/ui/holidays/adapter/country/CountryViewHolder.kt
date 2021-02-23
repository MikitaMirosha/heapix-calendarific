package com.heapix.calendarific.ui.holidays.adapter.country

import android.view.View
import com.heapix.calendarific.net.responses.country.CountryResponse
import com.heapix.calendarific.ui.base.adapters.BaseViewHolder
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_holiday_parameter.*

class CountryViewHolder(
    itemView: View,
    private val countryResponsePublishSubject: PublishSubject<CountryResponse>
) : BaseViewHolder<CountryResponse>(itemView) {

    override fun bind(model: CountryResponse) {
        setupCountryName(model)
        setupClickListener(model)
    }

    private fun setupCountryName(countryResponse: CountryResponse) {
        vTvParameterName.text = countryResponse.countryName
    }

    private fun setupClickListener(countryResponse: CountryResponse) {
        setOnClickListener {
            countryResponsePublishSubject.onNext(countryResponse)
        }
    }

}