package com.example.currencyapp.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.SortOrder
import com.example.currencyapp.databinding.FragmentSortBottomSheetBinding
import com.example.currencyapp.ui.rates.RatesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortBottomSheetFragment : BottomSheetDialogFragment(), SortAdapter.SortItemListener {

    private val viewModel: RatesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSortBottomSheetBinding.bind(view)

        val sortAdapter = SortAdapter(SortOrder.ALPHABET_ASC, this)
        binding.apply {
            sortPickerRecyclerView.apply {
                adapter = sortAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        sortAdapter.submitList(SortOrder.values().toList())
    }

    override fun onSortSelected(sort: SortOrder) {
        TODO("Not yet implemented")
    }


}