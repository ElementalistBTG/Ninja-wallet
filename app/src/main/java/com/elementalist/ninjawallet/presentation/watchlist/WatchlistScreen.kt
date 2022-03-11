package com.elementalist.ninjawallet.presentation.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
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

@Composable
fun WatchlistScreen(
    navController: NavController,
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val emptyList = viewModel.emptyList
    val percentageSelected = viewModel.percentageSelected.observeAsState()
    val currencySelected = viewModel.currencySelected.observeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadersLine(
                pricePercentageString = percentageSelected.value.toString(),
                currency = currencySelected.value.toString()
            )
            Divider(Modifier.padding(6.dp), color = Color.LightGray)
            if (!emptyList.value) {
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
            } else {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "Add coins to your watchlist to display here"
                )
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

        FloatingActionButton(
            onClick = { viewModel.refresh() },
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {
            Icon(
                Icons.Filled.Refresh,
                contentDescription = "refresh"
            )
        }

    }
}
