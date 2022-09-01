package com.example.currencyapp.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.databinding.FragmentFavoritesBinding
import com.example.currencyapp.ui.MainActivity
import com.example.currencyapp.ui.base.BaseRatesViewModel
import com.example.currencyapp.ui.rates.RatesAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), RatesAdapter.RateItemListener {

    private val viewModel: FavoritesViewModel by viewModels()

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

        viewModel.convert()
        lifecycleScope.launchWhenStarted {
            viewModel.result.collect { event ->
                when (event) {
                    is BaseRatesViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        ratesAdapter.submitList(event.rates)
                        (requireActivity() as MainActivity).updateToolbarTitle(
                            event.rates.first().base.uppercase(
                                Locale.getDefault()
                            )
                        )
                    }
                    is BaseRatesViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            activity,
                            getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is BaseRatesViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
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