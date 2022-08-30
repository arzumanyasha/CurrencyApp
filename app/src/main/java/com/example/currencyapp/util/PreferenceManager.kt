package com.example.currencyapp.util

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.example.currencyapp.data.model.local.Currency
import com.example.currencyapp.data.model.local.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferenceManager(context: Context) {
    private val dataStore = context.createDataStore("user_preferences")

    companion object {
        val SELECTED_CURRENCY = preferencesKey<Int>("selected_currency")
        val SELECTED_SORT = preferencesKey<Int>("selected_sort")
    }

    val baseCurrencyFlow: Flow<Currency> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            val baseCurrency = preference[SELECTED_CURRENCY] ?: 1

            when (baseCurrency) {
                0 -> Currency.USD
                1 -> Currency.BYN
                2 -> Currency.BNB
                3 -> Currency.BTC
                4 -> Currency.ETH
                5 -> Currency.EUR
                6 -> Currency.RUB
                7 -> Currency.ADA
                8 -> Currency.SOL
                else -> Currency.USD
            }
        }

    suspend fun setCurrency(currency: Currency) {
        dataStore.edit { preferences ->
            preferences[SELECTED_CURRENCY] = when (currency) {
                Currency.USD -> 0
                Currency.BYN -> 1
                Currency.BNB -> 2
                Currency.BTC -> 3
                Currency.ETH -> 4
                Currency.EUR -> 5
                Currency.RUB -> 6
                Currency.ADA -> 7
                Currency.SOL -> 8
            }
        }
    }

    val sortOptionFlow: Flow<SortOrder> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            val sortOption = preference[SELECTED_SORT] ?: 1

            when (sortOption) {
                0 -> SortOrder.ALPHABET_ASC
                1 -> SortOrder.ALPHABET_DESC
                2 -> SortOrder.VALUE_ASC
                3 -> SortOrder.VALUE_DESC
                else -> SortOrder.ALPHABET_ASC
            }
        }

    suspend fun setSortOption(sortOption: SortOrder) {
        dataStore.edit { preferences ->
            preferences[SELECTED_SORT] = when (sortOption) {
                SortOrder.ALPHABET_ASC -> 0
                SortOrder.ALPHABET_DESC -> 1
                SortOrder.VALUE_ASC -> 2
                SortOrder.VALUE_DESC -> 3
            }
        }
    }
}