package com.example.currencyapp.data.model.remote

import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.util.DateUtils
import kotlin.collections.ArrayList

object CurrencyMapper {
    fun toStorage(currencyResponse: CurrencyResponse) : List<Rate>{
        val rateList = ArrayList<Rate>()
        val date = DateUtils.getCurrentDate()
        val base = currencyResponse.rateData.keys.first()
        for(rate in currencyResponse.rateData[base]?.ratesMap!!) {
            rateList.add(Rate(
                id = "$base${rate.key}",
                base = base,
                currency = rate.key,
                date = date,
                value = rate.value,
                isFavorite = false
            ))
        }
        return rateList
    }
}
