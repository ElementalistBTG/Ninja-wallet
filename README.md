# Ninja-wallet

A cryptocurrency price tracker that uses the Coingecko API to display cryptocurrency prices and price fluctuations.

<p align="center">
  <img src="app/images/Screenshots/mainScreen.PNG" width="250" title="screenshot1">
  <img src="app/images/Screenshots/detailsScreen.PNG" width="250" title="screenshot2">
  <img src="app/images/Screenshots/searchScreen.PNG" width="250" title="screenshot3">
</p>

The app has a settings screen that enables you to change the currency that crypto are measured by and the time frame of the price fluctuations.
There is also a search function for quickly locating your favorite coin.
For each coin there are more informations and links provided by Coingecko API.
Last, you can add coins to your favorite list to be able to easily check their prices.

This app has been made for demonstration reasons. It is also published on both Play Store (Google) and AppGallery (Huawei).
<a href="https://play.google.com/store/apps/details?id=com.elementalist.ninjawallet"><img src="app/images/PlayStore.png" width="200" ></a>
<a href="https://play.google.com/store/apps/details?id=com.elementalist.ninjawallet"><img src="app/images/AppGallery.png" width="200" ></a>

Implementation details:
This app uses the MVVM pattern with CLEAN architecture. It is fully developed on jetpack compose.
Libraries used:
- Jetpack compose and the accompanist library for animated Scaffold features (TopBar hide/show) and "swipe-to-refresh" functionality
- Kotlin coroutines and Flow for asynchronous data fetch
- Dagger - Hilt for dependency injection
- Retrofit for the remote data fetching
- Room database for storing local data
- SharedPreferences are also used for the settings parameters
- Splash screen (Android 12)
- Firebase analytics, crashlytics and performance for monitoring
- Coil for image loading

The app is also configured for building-deploying using Bitrise

You can also check out my article on [medium](https://medium.com/@elementalistbtg/expandable-and-clickable-text-with-annotated-string-in-jetpack-compose-a49481a55477) about annotated Strings in jetpack compose and how i used them in my app

