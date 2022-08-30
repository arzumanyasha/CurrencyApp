package com.example.currencyapp.ui.rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.data.model.local.SortOrder
import com.example.currencyapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val rates: List<Rate>) : CurrencyEvent()
        class Failure(val errorText: String) : CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()
    }

    val sortOrder = MutableStateFlow(SortOrder.ALPHABET_DESC)

    private val _result = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val result: StateFlow<CurrencyEvent> = _result

    fun convert(isFavorite: Boolean = false) =
        viewModelScope.launch {
            _result//repository.getRatesByCurrency("usd")
                .combine(sortOrder) { _, sortOrder ->
                    repository.getRatesByCurrency("usd", sortOrder, isFavorite).collect {
                        _result.value = CurrencyEvent.Success(it)
                    }
                }.collect()
        }

    fun onRateFavorite(rate: Rate, isFavorite: Boolean) = viewModelScope.launch {
        if (isFavorite) {
            repository.addToFavorites(rate)
        } else {
            repository.removeFromFavorites(rate)
        }
    }

}

