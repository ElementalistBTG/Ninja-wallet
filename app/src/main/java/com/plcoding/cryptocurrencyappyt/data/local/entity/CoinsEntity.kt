package com.plcoding.cryptocurrencyappyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinsEntity(
    @PrimaryKey val id: String
)
