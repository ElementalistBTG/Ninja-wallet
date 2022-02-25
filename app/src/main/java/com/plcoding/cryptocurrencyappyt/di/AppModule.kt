package com.plcoding.cryptocurrencyappyt.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cryptocurrencyappyt.common.Constants
import com.plcoding.cryptocurrencyappyt.data.local.PortfolioDatabase
import com.plcoding.cryptocurrencyappyt.data.remote.CoinGeckoApi
import com.plcoding.cryptocurrencyappyt.data.repository.CoinRepositoryImpl
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
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
        db: PortfolioDatabase
    ) : CoinRepository{
        return CoinRepositoryImpl(api,db.portfolioDao) //uses the above function!!!!!
    }

    @Provides
    @Singleton
    fun providePortfolioDatabase(app: Application): PortfolioDatabase{
        return Room.databaseBuilder(
            app, PortfolioDatabase::class.java,"portfolio_db")
            .build()
    }

}