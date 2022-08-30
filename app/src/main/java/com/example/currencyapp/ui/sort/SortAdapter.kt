package com.example.currencyapp.ui.sort

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.data.model.local.SortOrder
import com.example.currencyapp.databinding.SortItemBinding

class SortAdapter(private val selectedSort: SortOrder,
                  private val listener: SortItemListener
) : ListAdapter<SortOrder, SortAdapter.SortViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortViewHolder {
        val binding =
            SortItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SortViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SortViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SortViewHolder(private val binding: SortItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                sortTextView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val sort = getItem(position)
                        listener.onSortSelected(sort)
                    }
                }
            }
        }

        fun bind(sort: SortOrder) {
            binding.apply {
                sortTextView.text = sort.name
                if (sort == selectedSort) {
                    selectedImageView.visibility = View.VISIBLE
                } else {
                    selectedImageView.visibility = View.GONE
                }
            }
        }
    }

    interface SortItemListener {
        fun onSortSelected(sort: SortOrder)
    }

    class DiffCallback : DiffUtil.ItemCallback<SortOrder>() {
        override fun areItemsTheSame(oldItem: SortOrder, newItem: SortOrder) =
            oldItem.ordinal == newItem.ordinal

        override fun areContentsTheSame(oldItem: SortOrder, newItem: SortOrder) =
            oldItem == newItem
    }
}