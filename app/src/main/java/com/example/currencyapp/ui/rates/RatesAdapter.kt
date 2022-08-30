package com.example.currencyapp.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.databinding.RateItemBinding

class RatesAdapter(private val listener: RateItemListener) : ListAdapter<Rate, RatesAdapter.RateViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding = RateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class RateViewHolder(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                favoriteImageButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val rate = getItem(position)
                        listener.onFavoriteClick(rate, !rate.isFavorite)
                    }
                }
            }
        }

        fun bind(rate: Rate) {
            binding.apply {
                rateNameTextView.text = rate.currency
                rateValueTextView.text = rate.value.toString()
                if (rate.isFavorite) {
                    setFavoriteViewStatus(R.drawable.ic_favorite_heart_24)
                } else {
                    setFavoriteViewStatus(R.drawable.ic_favorite_border_heart_24)
                }
            }
        }

        private fun setFavoriteViewStatus(resourceIndex: Int) {
            binding.favoriteImageButton.apply {
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        resourceIndex,
                        null
                    )
                )
            }
        }
    }

    interface RateItemListener {
        fun onFavoriteClick(rate: Rate, isFavorite: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate) =
            oldItem == newItem
    }
}

