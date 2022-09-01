package com.example.currencyapp.ui.rates

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.databinding.FragmentRatesBinding
import com.example.currencyapp.ui.MainActivity
import com.example.currencyapp.ui.base.BaseRatesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

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