package com.elementalist.ninjawallet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//a class to give Hilt access to our application
@HiltAndroidApp
class CoinApplication : Application()