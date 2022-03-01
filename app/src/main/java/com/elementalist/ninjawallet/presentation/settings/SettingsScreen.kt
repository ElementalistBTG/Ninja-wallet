package com.elementalist.ninjawallet.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
//nav controller is not needed since we don't navigate to anywhere through this screen
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var textFieldState by remember { mutableStateOf(TextFieldValue("250")) }
    val coinsText by viewModel.coinsEntered.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        //verticalArrangement = Arrangement.SpaceBetween
        //.wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Settings Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Divider(modifier = Modifier.padding(3.dp))
        Text(
            text = "Display price change percentage in units:",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        PriceChangePercentageRadioGroup(viewModel)
        Divider(modifier = Modifier.padding(5.dp), color = Color.Black)
        Text(
            text = "Value denominator currency:",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        CurrenciesRadioGroup(viewModel)
        Divider(modifier = Modifier.padding(5.dp), color = Color.Black)
        Text(
            text = "Coins displayed:",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextField(
            value = TextFieldValue(coinsText.toString()),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newInt -> viewModel.coinNumberChange(newInt.text) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


