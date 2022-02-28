package com.elementalist.ninjawallet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elementalist.ninjawallet.data.local.entity.CoinsEntity

@Database(
    entities = [CoinsEntity::class],
    version = 1,
    exportSchema = false
)

abstract class WatchlistDatabase: RoomDatabase(){
    abstract val watchlistDao : WatchlistDao

}