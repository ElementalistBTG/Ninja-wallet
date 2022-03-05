package com.elementalist.ninjawallet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elementalist.ninjawallet.R
import com.elementalist.ninjawallet.presentation.Watchlist.WatchlistScreen
import com.elementalist.ninjawallet.presentation.coin_detail.CoinDetailScreen
import com.elementalist.ninjawallet.presentation.coin_list.CoinListScreen
import com.elementalist.ninjawallet.presentation.coin_list.CoinListViewModel
import com.elementalist.ninjawallet.presentation.search.SearchScreen
import com.elementalist.ninjawallet.presentation.search.SearchViewModel
import com.elementalist.ninjawallet.presentation.settings.SettingsScreen
import com.elementalist.ninjawallet.ui.theme.CryptocurrencyAppYTTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint //allows hilt to inject dependencies into this activity or sub-composable
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
fun MainScreenView(
    //must find a way to do this better!
    viewModel: SearchViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    // State of topBar, set state to false, if current page route is anything other than CoinListScreen
    val topBarState = rememberSaveable { (mutableStateOf(true)) }
    val secondaryScreen = rememberSaveable { (mutableStateOf(false)) }
    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        "coin_list_screen" -> {
            topBarState.value = true
            secondaryScreen.value = false
        }
        "coin_detail_screen" -> {
            topBarState.value = false
            secondaryScreen.value = true
        }
        "Watchlist_screen" -> {
            topBarState.value = true
            secondaryScreen.value = false
        }
        "settings_screen" -> {
            topBarState.value = true
            secondaryScreen.value = true
        }
    }
    com.google.accompanist.insets.ui.Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                topBarState = topBarState,
                secondaryScreen = secondaryScreen,
                viewModel = viewModel
            )
        },
        bottomBar = { BottomNavigation(navController = navController) },
        content = {
            // ovoid top and bottom bar overlay content by using the padding values
            Column(modifier = Modifier.padding(it)) {
                NavigationGraph(navController = navController)
            }
        }
    )
}


@Composable
fun AppTopBar (
    navController: NavController,
    topBarState: MutableState<Boolean>,
    secondaryScreen: MutableState<Boolean>,
    viewModel: SearchViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val searchClicked = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val titleText: String = when (navBackStackEntry?.destination?.route ?: "coin_list_screen") {
        "coin_list_screen" -> "Ninja Wallet"
        "coin_detail_screen" -> "Ninja Wallet"
        "Watchlist_screen" -> "Watchlist"
        "settings_screen" -> "Settings"
        else -> "Ninja Wallet"
    }
    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            if (secondaryScreen.value) {
                SecondaryScreenTopAppBar(navController, titleText)
            } else {
                if (searchClicked.value) {
                    SearchTopAppBar(navController,viewModel, searchClicked, focusManager)
                } else {
                    GeneralTopAppBar(navController, titleText, searchClicked)
                }
            }
        }
    )
}

@Composable
private fun GeneralTopAppBar(
    navController: NavController,
    titleText: String,
    searchClicked: MutableState<Boolean>
) {
    TopAppBar(
        backgroundColor = Color.DarkGray,
        elevation = 10.dp
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
                text = titleText,
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
                    searchClicked.value = true
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchTopAppBar(
    navController: NavController,
    viewModel: SearchViewModel,
    searchClicked: MutableState<Boolean>,
    focusManager: FocusManager
) {
    val focusRequester = FocusRequester()
    val coinSearch = remember {
        mutableStateOf("")
    }
    TopAppBar(
        backgroundColor = Color.DarkGray,
        elevation = 10.dp
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .focusRequester(focusRequester),
            value = coinSearch.value,
            onValueChange = { value ->
                coinSearch.value = value
            },
            placeholder = {
                Text(text = "Search Cryptos")
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        searchClicked.value = false
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.searchCoin("")
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
                navController.navigate(Screen.SearchScreen.route)
            })
        )
    }
    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }
}

@Composable
private fun SecondaryScreenTopAppBar(
    navController: NavController,
    titleText: String
) {
    TopAppBar(
        backgroundColor = Color.DarkGray,
        elevation = 10.dp
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (backArrow, title) = createRefs()
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .constrainAs(backArrow) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                Icon(Icons.Filled.ArrowBack, "BackArrow")
            }
            Text(
                text = titleText,
                color = Color.Green,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )
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
        composable(
            route = Screen.SearchScreen.route
        ) {
            SearchScreen(navController)
        }
    }
}
