package com.elementalist.ninjawallet.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elementalist.ninjawallet.R
import com.elementalist.ninjawallet.presentation.Screen
import com.elementalist.ninjawallet.presentation.components.CoinListItem
import com.elementalist.ninjawallet.presentation.components.HeadersLine

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val percentageSelected = viewModel.percentageSelected.observeAsState()
    val currencySelected = viewModel.currencySelected.observeAsState()
    val state = viewModel.state

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(viewModel)
            Divider(Modifier.padding(6.dp), color = Color.LightGray)
            HeadersLine(
                pricePercentageString = percentageSelected.value.toString(),
                currency = currencySelected.value.toString()
            )
            Divider(Modifier.padding(6.dp), color = Color.LightGray)
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

@Composable
fun SearchBar(
    viewModel: SearchViewModel
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .focusRequester(focusRequester),
        value = viewModel.query.value,
        onValueChange = { newValue ->
            viewModel.onQueryChange(newValue)
        },
        placeholder = {
            Text(text = "Search Cryptos")
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    viewModel.onQueryChange("")
                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            }
        },
        textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
        singleLine = true,
        shape =
        RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorResource(id = R.color.medium_gray),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
            viewModel.refresh()
        })
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
