package com.example.currencyapp.data.model.remote

data class CurrencyResponse (
    val date : String,
    val rateData : Map<String, Rates>
)