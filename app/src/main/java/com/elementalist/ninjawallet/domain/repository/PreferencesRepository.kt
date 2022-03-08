package com.elementalist.ninjawallet.domain.repository

import android.content.SharedPreferences

interface PreferencesRepository {

    val pref: SharedPreferences
    val editor: SharedPreferences.Editor

    fun clearData()

    fun getPriceChangePercentage(): String

    fun setPriceChangePercentage(percentage: String)

    fun getCurrency(): String

    fun setCurrency(currency: String)

    fun getCoinsDisplayedNumber(): Int

    fun setCoinsDisplayedNumber(coins: Int)

}