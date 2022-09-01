package com.example.currencyapp.ui.favorites

import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.repository.CurrencyRepository
import com.example.currencyapp.ui.base.BaseRatesViewModel
import com.example.currencyapp.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val preferenceManager: PreferenceManager
) : BaseRatesViewModel(repository, preferenceManager) {
    override fun convert() {
        viewModelScope.launch {
            currentCurrency.combine(sortOrder) { currentCurrency, sortOrder ->
                Pair(currentCurrency, sortOrder)
            }.collectLatest { (currentCurrency, sortOrder) ->
                _result.value = CurrencyEvent.Loading
                try {
                    repository.getFavoriteRates(
                        currentCurrency.name.lowercase(Locale.getDefault()),
                        sortOrder
                    ).collect {
                        _result.value = CurrencyEvent.Success(it)
                    }
                } catch (ex: Exception) {
                    _result.value = CurrencyEvent.Failure(ex.localizedMessage)
                }
            }
        }
    }
}