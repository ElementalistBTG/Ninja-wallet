package com.plcoding.cryptocurrencyappyt.data.local

import androidx.room.*
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity

@Dao
interface PortfolioDao {

    @Query("SELECT * FROM CoinsEntity")
    suspend fun getPortfolioCoins(): List<CoinsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinToPortfolio(coin:CoinsEntity)

    @Delete
    suspend fun deleteCoinFromPortfolio(coin: CoinsEntity)
}