package com.example.currencyapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyapp.data.model.local.Rate
import com.example.currencyapp.ui.rates.SortOrder
import com.example.currencyapp.util.DateUtils
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {
    @Query("SELECT * FROM rate_table WHERE base = :base ORDER BY " +
            "CASE WHEN :sortOption = 0 THEN currency END ASC, " +
            "CASE WHEN :sortOption = 1 THEN currency END DESC, " +
            "CASE WHEN :sortOption = 2 THEN value END ASC, " +
            "CASE WHEN :sortOption = 3 THEN value END DESC")
    fun getRates(base: String, sortOption: Int) : Flow<List<Rate>>

    @Query("SELECT * FROM rate_table WHERE base = :base AND isFavorite = 1 ORDER BY id")
    fun getFavoriteRates(base: String/*, sortOption: String? = null*/) : Flow<List<Rate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRates(rates: List<Rate>)

    @Query("UPDATE rate_table SET date = :date AND value = :value WHERE id = :id")
    suspend fun updateRate(id: String, date: String = DateUtils.getCurrentDate(), value: Double)

    @Query("UPDATE rate_table SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

}