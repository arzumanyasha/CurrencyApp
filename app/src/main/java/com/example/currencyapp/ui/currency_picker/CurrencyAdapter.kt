package com.example.currencyapp.ui.currency_picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.data.model.local.Currency
import com.example.currencyapp.databinding.CurrencyItemBinding

class CurrencyAdapter(
    private val selectedCurrency: Currency,
    private val listener: CurrencyItemListener
) : ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CurrencyViewHolder(private val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                currencyTextView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val currency = getItem(position)
                        listener.onCurrencySelected(currency)
                    }
                }
            }
        }

        fun bind(currency: Currency) {
            binding.apply {
                currencyTextView.text = currency.name
                if (currency == selectedCurrency) {
                    selectedImageView.visibility = View.VISIBLE
                } else {
                    selectedImageView.visibility = View.GONE
                }
            }
        }
    }

    interface CurrencyItemListener {
        fun onCurrencySelected(currency: Currency)
    }

    class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) =
            oldItem.ordinal == newItem.ordinal

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) =
            oldItem == newItem
    }
}