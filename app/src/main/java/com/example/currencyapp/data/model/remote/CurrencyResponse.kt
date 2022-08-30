package com.example.currencyapp.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyResponse (
    val date : String,
    val rateData : Map<String, Rates>
)