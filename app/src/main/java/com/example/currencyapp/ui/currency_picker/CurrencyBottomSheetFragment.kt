package com.example.currencyapp.ui.currency_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.Currency
import com.example.currencyapp.databinding.FragmentCurrencyBottomSheetBinding
import com.example.currencyapp.ui.rates.RatesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyBottomSheetFragment : BottomSheetDialogFragment(), CurrencyAdapter.CurrencyItemListener {

    private val viewModel: RatesViewModel by viewModels()
    private lateinit var binding: FragmentCurrencyBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCurrencyBottomSheetBinding.bind(view)

        lifecycleScope.launch {
            viewModel.currentCurrency.collect { selectedCurrency ->
                setCurrencyList(selectedCurrency)
            }
        }
    }

    private fun setCurrencyList(selectedCurrency: Currency) {
        val currencyAdapter = CurrencyAdapter(selectedCurrency, this)
        binding.apply {
            currencyPickerRecyclerView.apply {
                adapter = currencyAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        currencyAdapter.submitList(Currency.values().toList())
    }

    override fun onCurrencySelected(currency: Currency) {
        viewModel.setCurrency(currency)
        dismiss()
    }
}