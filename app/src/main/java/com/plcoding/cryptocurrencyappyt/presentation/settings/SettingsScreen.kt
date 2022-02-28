package com.plcoding.cryptocurrencyappyt.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(
//nav controller is not needed since we don't navigate to anywhere through this screen
    viewModel: SettingsViewModel = hiltViewModel()
) {
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
    }
}


