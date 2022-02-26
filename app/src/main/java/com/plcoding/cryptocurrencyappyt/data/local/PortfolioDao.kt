package com.plcoding.cryptocurrencyappyt.data.local

import androidx.room.*
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM CoinsEntity")
    suspend fun getWatchlistCoins(): List<CoinsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinToWatchlist(coin:CoinsEntity)

    @Delete
    suspend fun deleteCoinFromWatchlist(coin: CoinsEntity)
}