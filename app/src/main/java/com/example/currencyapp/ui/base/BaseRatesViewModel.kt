package com.example.currencyapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.model.local.Currency
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.data.model.local.SortOrder
import com.example.currencyapp.data.repository.CurrencyRepository
import com.example.currencyapp.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseRatesViewModel constructor(
    private val repository: CurrencyRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    sealed class CurrencyEvent {
        class Success(val rates: List<Rate>) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    val sortOrder = preferenceManager.sortOptionFlow
    val currentCurrency = preferenceManager.baseCurrencyFlow

    protected val _result = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val result = _result.asStateFlow()

    abstract fun convert()

    fun setCurrency(currency: Currency) {
        viewModelScope.launch {
            preferenceManager.setCurrency(currency)
        }
    }

    fun setOrder(sortOrder: SortOrder) {
        viewModelScope.launch {
            preferenceManager.setSortOption(sortOrder)
        }
    }

    fun onRateFavorite(rate: Rate, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            repository.addToFavorites(rate)
        } else {
            repository.removeFromFavorites(rate)
        }
    }
}