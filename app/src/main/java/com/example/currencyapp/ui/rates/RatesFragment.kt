package com.example.currencyapp.ui.rates

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.databinding.FragmentRatesBinding
import com.example.currencyapp.ui.rates.RatesViewModel.CurrencyEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatesFragment : Fragment(R.layout.fragment_rates), RatesAdapter.RateItemListener {

    private val viewModel: RatesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRatesBinding.bind(view)

        val ratesAdapter = RatesAdapter(this)
        binding.apply {
            ratesRecyclerView.apply {
                adapter = ratesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.convert(false)
        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { event ->
                when(event) {
                    is CurrencyEvent.Success -> {
                        ratesAdapter.submitList(event.rates)
                    }
                    is CurrencyEvent.Failure -> {

                    }
                    is CurrencyEvent.Loading -> {

                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onFavoriteClick(rate: Rate, isFavorite: Boolean) {
        viewModel.onRateFavorite(rate, isFavorite)
    }
}