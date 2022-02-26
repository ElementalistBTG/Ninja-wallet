package com.plcoding.cryptocurrencyappyt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plcoding.cryptocurrencyappyt.presentation.coin_detail.CoinDetailScreen
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListScreen
import com.plcoding.cryptocurrencyappyt.presentation.coin_list.CoinListViewModel
import com.plcoding.cryptocurrencyappyt.presentation.Watchlist.WatchlistScreen
import com.plcoding.cryptocurrencyappyt.presentation.settings.SettingsScreen
import com.plcoding.cryptocurrencyappyt.ui.theme.CryptocurrencyAppYTTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint //allows hilt to inject dependancies into this activity or sub-composables
class MainActivity : ComponentActivity() {

    private val coinListViewModel: CoinListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                //as long as we our loading our splash screen will show up
                coinListViewModel.isRefreshing.value
            }
        }
        setContent {
            CryptocurrencyAppYTTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            AppTopBar(navController = navController)
        },
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        // ovoid bottom bar overlay content
        Column(modifier = Modifier.padding(bottom = 58.dp)) {
            NavigationGraph(navController = navController)
        }
    }
}


@Composable
fun AppTopBar(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.DarkGray,
        elevation = 10.dp,
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (title, settings, search) = createRefs()

            IconButton(
                onClick = {
                    navController.navigate(Screen.SettingsScreen.route)
                },
                modifier = Modifier
                    .constrainAs(settings) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(title.start)
                    }
            ) {
                Icon(Icons.Filled.Settings, "Settings")
            }
            Text(
                text = "Ninja-Coin Wallet",
                color = Color.Green,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(settings.end)
                        end.linkTo(search.start)
                        width = Dimension.fillToConstraints
                    }
            )
            IconButton(
                onClick = {
                    /* doSomething() */

                },
                modifier = Modifier
                    .constrainAs(search) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                Icon(Icons.Filled.Search, "Search")
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        Screen.CoinListScreen,
        Screen.WatchlistScreen
    )
    BottomNavigation(
        backgroundColor = Color.DarkGray,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    item.icon?.let { painterResource(it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = item.route
                        )
                    }
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.CoinListScreen.route)
                        restoreState = true
                        launchSingleTop = true

                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.CoinListScreen.route
    ) {
        composable(
            route = Screen.CoinListScreen.route
        ) {
            CoinListScreen(navController)
        }
        composable(
            route = Screen.CoinDetailScreen.route + "/{coinId}"
        ) {
            CoinDetailScreen()
        }
        composable(
            route = Screen.WatchlistScreen.route
        ) {
            WatchlistScreen(navController)
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen()
        }
    }
}
