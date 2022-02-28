package com.plcoding.cryptocurrencyappyt.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.data.local.Preferences.PreferencesRepositoryImpl
import com.plcoding.cryptocurrencyappyt.data.local.WatchlistDatabase
import com.plcoding.cryptocurrencyappyt.data.remote.CoinGeckoApi
import com.plcoding.cryptocurrencyappyt.data.repository.CoinRepositoryImpl
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import com.plcoding.cryptocurrencyappyt.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//this ensures that it lives as long as our application
object AppModule {

    @Provides//because the function provides something (a dependency)
    @Singleton //there is a single instance throughout our whole app
    fun provideCoinGeckoApi() : CoinGeckoApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoApi::class.java) //the api we want to create
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        api: CoinGeckoApi,
        db: WatchlistDatabase
    ) : CoinRepository{
        return CoinRepositoryImpl(api,db.watchlistDao) //uses the above function!!!!!
    }

    @Provides
    @Singleton
    fun provideWatchlistDatabase(app: Application): WatchlistDatabase{
        return Room.databaseBuilder(
            app, WatchlistDatabase::class.java,"Watchlist_db")
            .build()
    }

    @Provides
    @Singleton
    fun providesPreferencesRepository(
        app: Application
    ) : PreferencesRepository {
        return PreferencesRepositoryImpl(app.applicationContext)
    }
}