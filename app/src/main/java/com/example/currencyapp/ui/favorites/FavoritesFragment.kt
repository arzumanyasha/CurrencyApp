package com.example.currencyapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.databinding.FragmentFavoritesBinding
import com.example.currencyapp.ui.rates.RatesAdapter
import com.example.currencyapp.ui.rates.RatesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), RatesAdapter.RateItemListener {

    private val viewModel: RatesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoritesBinding.bind(view)

        val ratesAdapter = RatesAdapter(this)
        binding.apply {
            favoriteRatesRecyclerView.apply {
                adapter = ratesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.convert(true)
        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { event ->
                when(event) {
                    is RatesViewModel.CurrencyEvent.Success -> {
                        ratesAdapter.submitList(event.rates)
                    }
                    is RatesViewModel.CurrencyEvent.Failure -> {

                    }
                    is RatesViewModel.CurrencyEvent.Loading -> {

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