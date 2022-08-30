package com.example.currencyapp.data.api

import com.example.currencyapp.data.model.remote.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApi {
    @GET("currencies/{base}.json")
    suspend fun getRates(
        @Path("base") base: String,
    ): Response<CurrencyResponse>
}