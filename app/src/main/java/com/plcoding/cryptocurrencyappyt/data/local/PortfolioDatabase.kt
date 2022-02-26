package com.plcoding.cryptocurrencyappyt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cryptocurrencyappyt.data.local.entity.CoinsEntity

@Database(
    entities = [CoinsEntity::class],
    version = 1,
    exportSchema = false
)

abstract class WatchlistDatabase: RoomDatabase(){
    abstract val WatchlistDao : WatchlistDao

}