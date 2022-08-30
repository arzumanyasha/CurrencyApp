package com.example.currencyapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyapp.data.db.AppDatabase.Companion.DB_VERSION
import com.example.currencyapp.data.model.local.Rate

@Database(
    entities = [Rate::class],
    version = DB_VERSION,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "Currency.db"
        const val RATES_TABLE = "rate_table"
        const val CURRENCY_TABLE = "currency_table"

        /*fun create(applicationContext: Context): AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()*/
    }
}