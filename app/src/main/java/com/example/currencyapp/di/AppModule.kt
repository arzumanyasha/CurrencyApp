package com.example.currencyapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.currencyapp.data.api.CurrencyApi
import com.example.currencyapp.data.db.AppDatabase
import com.example.currencyapp.data.db.RateDao
import com.example.currencyapp.data.model.remote.CurrencyResponse
import com.example.currencyapp.data.model.remote.CurrencyResponseDeserializer
import com.example.currencyapp.data.repository.CurrencyRepository
import com.example.currencyapp.util.PreferenceManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapter(
                    CurrencyResponse::class.java,
                    CurrencyResponseDeserializer()
                ).create()
            )
        )
        .build()
        .create(CurrencyApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideRateDao(db: AppDatabase) = db.rateDao()

    @Singleton
    @Provides
    fun provideCurrencyRepository(rateDao: RateDao, api: CurrencyApi): CurrencyRepository =
        CurrencyRepository(rateDao, api)

    @Singleton
    @Provides
    fun providePreferencesManager(@ApplicationContext context: Context): PreferenceManager =
        PreferenceManager(context)
}