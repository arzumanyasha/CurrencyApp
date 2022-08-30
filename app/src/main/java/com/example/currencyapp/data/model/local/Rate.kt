package com.example.currencyapp.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rate_table")
data class Rate(
    @PrimaryKey
    val id: String = "",
    val base: String,
    val currency: String,
    val date: String,
    val value: Double,
    val isFavorite: Boolean = false
)