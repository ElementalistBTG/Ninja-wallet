package com.plcoding.cryptocurrencyappyt.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
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
        DisplayRadioGroup()

    }

}

@Composable
fun DisplayRadioGroup() {
    var selected by remember { mutableStateOf("1hr") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(
                unbounded = false
            )
            .padding(10.dp)
    ) {
        Column() {
            Text(
                text = "1hr",
                modifier = Modifier
                    .clickable(onClick = { selected = "1hr" })
            )
            RadioButton(selected = selected == "1hr", onClick = { selected = "1hr" })
        }
        Spacer(modifier = Modifier.size(4.dp))

        Column() {
            Text(
                text = "24hr",
                modifier = Modifier
                    .clickable(onClick = { selected = "24hr" })
            )
            RadioButton(selected = selected == "24hr", onClick = { selected = "24hr" })
        }
        Spacer(modifier = Modifier.size(4.dp))

        Column() {
            Text(
                text = "7d",
                modifier = Modifier
                    .clickable(onClick = { selected = "7d" })
            )
            RadioButton(selected = selected == "7d", onClick = { selected = "7d" })
        }
        Spacer(modifier = Modifier.size(4.dp))

        Column() {
            Text(
                text = "14d",
                modifier = Modifier
                    .clickable(onClick = { selected = "14d" })
            )
            RadioButton(selected = selected == "14d", onClick = { selected = "14d" })
        }
        Spacer(modifier = Modifier.size(4.dp))

        Column() {
            Text(
                text = "30d",
                modifier = Modifier
                    .clickable(onClick = { selected = "30d" })
            )
            RadioButton(selected = selected == "30d", onClick = { selected = "30d" })
        }

    }
}