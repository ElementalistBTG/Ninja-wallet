package com.elementalist.ninjawallet.presentation.Watchlist

import com.elementalist.ninjawallet.domain.model.Coin

data class WatchlistState(
    val isLoading: Boolean = false,
    val coins : List<Coin> = emptyList(),
    val error: String = ""
)
