package com.plcoding.cryptocurrencyappyt.presentation.Watchlist

import com.plcoding.cryptocurrencyappyt.domain.model.Coin

data class WatchlistState(
    val isLoading: Boolean = false,
    val coins : List<Coin> = emptyList(),
    val error: String = ""
)
