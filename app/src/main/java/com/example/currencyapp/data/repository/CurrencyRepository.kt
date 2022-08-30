package com.example.currencyapp.data.repository

import android.util.Log
import com.example.currencyapp.data.api.CurrencyApi
import com.example.currencyapp.data.db.RateDao
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.data.model.remote.CurrencyMapper
import com.example.currencyapp.data.model.remote.CurrencyResponse
import com.example.currencyapp.ui.rates.SortOrder
import com.example.currencyapp.util.DateUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val dao: RateDao,
    private val api: CurrencyApi
) {
    fun getRatesByCurrency(currency: String, sortOption: SortOrder, isFavorite: Boolean = false): Flow<List<Rate>> = flow {
        var rateList = ArrayList<Rate>()
        if (!isFavorite) {
            dao.getRates(currency, sortOption.ordinal).collect {
                if (it.isEmpty()) {
                    val response = api.getRates(currency)
                    val ratesList = CurrencyMapper.toStorage(response.body()!!)
                    emit(ratesList)
                    dao.addRates(ratesList)
                    Log.d("ddd", "from network")
                } else {
                    if (it.first().date == DateUtils.getCurrentDate()) {
                        emit(it)
                    } else {
                        val response = api.getRates(currency)
                        val ratesList = CurrencyMapper.toStorage(response.body()!!)
                        ratesList.forEach { rate ->
                            dao.updateRate(
                                id = rate.id,
                                value = rate.value
                            )
                        }
                        emit(ratesList)
                    }
                    Log.d("ddd", "from db")
                }
            }
        } else {
            dao.getFavoriteRates(currency).collect {
                emit(it)
            }
        }
    }
        .flowOn(Dispatchers.IO)

    suspend fun addToFavorites(rate: Rate) {
        dao.updateFavorite(rate.id, true)
    }

    suspend fun removeFromFavorites(rate: Rate) {
        dao.updateFavorite(rate.id, false)
    }

}