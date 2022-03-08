package com.elementalist.ninjawallet.presentation.coin_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elementalist.ninjawallet.presentation.Screen
import com.elementalist.ninjawallet.presentation.components.CoinListItem
import com.elementalist.ninjawallet.presentation.components.HeadersLine
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val percentageSelected = viewModel.percentageSelected.observeAsState()
    val currencySelected = viewModel.currencySelected.observeAsState()
    val state by viewModel.myState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadersLine(
                pricePercentageString = percentageSelected.value.toString(),
                currency = currencySelected.value.toString()
            )
            Divider(Modifier.padding(6.dp), color = Color.LightGray)
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    viewModel.refresh()
                }) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(state.coins) { coin ->
                        CoinListItem(
                            coin = coin,
                            onItemClick = {
                                navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                            },
                            pricePercentage = percentageSelected.value.toString()
                        )
                        Divider(Modifier.padding(3.dp), color = Color.Green)
                    }
                }
            }

        }

        if (state.error.isNotBlank()) {//if it contains an error
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )

        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}