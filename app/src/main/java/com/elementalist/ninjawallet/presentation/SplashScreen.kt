package com.elementalist.ninjawallet.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.elementalist.ninjawallet.R
import kotlinx.coroutines.delay

//In order to use this screen you must adapt the navigation for each screen separately
//Not used currently

@Composable
fun SplashScreen(navController: NavController?) {
    val scale = remember {
        Animatable(20f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 30f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(1000L)
        navController?.navigate("coin_list_screen")
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(0.6f),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Crop
            )
            Text(text = "Powered by the CoinGecko API", color = Color.Green)
        }
    }

}
