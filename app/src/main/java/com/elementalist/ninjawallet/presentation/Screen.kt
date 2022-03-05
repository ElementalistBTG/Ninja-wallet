package com.elementalist.ninjawallet.presentation

import com.elementalist.ninjawallet.R


sealed class Screen(val route: String, val icon: Int? = null){
    object CoinListScreen: Screen("coin_list_screen", icon = R.drawable.baseline_list)
    object CoinDetailScreen: Screen("coin_detail_screen")
    //bottom bar
    object WatchlistScreen: Screen("Watchlist_screen", icon = R.drawable.baseline_watchlist)
    //top bar settings
    object SettingsScreen: Screen("settings_screen")
    //search screen
    object SearchScreen: Screen("search_screen")
}

