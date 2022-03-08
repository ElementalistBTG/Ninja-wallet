package com.elementalist.ninjawallet.di

import android.app.Application
import androidx.room.Room
import com.elementalist.ninjawallet.common.Constants
import com.elementalist.ninjawallet.data.local.Database.WatchlistDatabase
import com.elementalist.ninjawallet.data.remote.CoinGeckoApi
import com.elementalist.ninjawallet.data.repository.CoinRepositoryImpl
import com.elementalist.ninjawallet.data.repository.PreferencesRepositoryImpl
import com.elementalist.ninjawallet.domain.repository.CoinRepository
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository
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
    fun provideCoinGeckoApi(): CoinGeckoApi {
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
    ): CoinRepository {
        return CoinRepositoryImpl(api, db.watchlistDao) //uses the above function!!!!!
    }

    @Provides
    @Singleton
    fun provideWatchlistDatabase(app: Application): WatchlistDatabase {
        return Room.databaseBuilder(
            app, WatchlistDatabase::class.java, "Watchlist_db"
        )
            .build()
    }

    @Provides
    @Singleton
    fun providesPreferencesRepository(
        app: Application
    ): PreferencesRepository {
        return PreferencesRepositoryImpl(app.applicationContext)
    }

}