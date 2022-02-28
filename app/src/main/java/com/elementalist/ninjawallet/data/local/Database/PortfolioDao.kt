package com.elementalist.ninjawallet.data.local

import androidx.room.*
import com.elementalist.ninjawallet.data.local.entity.CoinsEntity

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM CoinsEntity")
    suspend fun getWatchlistCoins(): List<CoinsEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM CoinsEntity WHERE id = :id)")
    suspend fun exists(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinToWatchlist(coin:CoinsEntity)

    @Delete
    suspend fun deleteCoinFromWatchlist(coin: CoinsEntity)
}