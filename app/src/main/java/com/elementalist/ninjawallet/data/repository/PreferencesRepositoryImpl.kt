package com.elementalist.ninjawallet.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.elementalist.ninjawallet.common.Constants.curr2
import com.elementalist.ninjawallet.common.Constants.priceChange2
import com.elementalist.ninjawallet.data.local.Preferences.COINS_DISPLAYED
import com.elementalist.ninjawallet.data.local.Preferences.CURRENCY
import com.elementalist.ninjawallet.data.local.Preferences.PREFERENCE_NAME
import com.elementalist.ninjawallet.data.local.Preferences.PRICE_CHANGE_PERCENTAGE
import com.elementalist.ninjawallet.domain.repository.PreferencesRepository

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {

    override val pref: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    override val editor: SharedPreferences.Editor = pref.edit()

    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)

    override fun clearData() {
        editor.clear()
        editor.commit()
    }

    override fun getPriceChangePercentage(): String {
        val prefValue = PRICE_CHANGE_PERCENTAGE.getString()
        return if (prefValue == "") priceChange2 else prefValue
    }

    override fun setPriceChangePercentage(percentage: String) =
        PRICE_CHANGE_PERCENTAGE.put(percentage)

    override fun getCurrency(): String {
        val prefValue = CURRENCY.getString()
        return if (prefValue == "") curr2 else prefValue
    }

    override fun setCurrency(currency: String) =
        CURRENCY.put(currency)

    override fun getCoinsDisplayedNumber(): Int {
        val prefValue = COINS_DISPLAYED.getInt()
        return if (prefValue == 0) 250 else prefValue
    }

    override fun setCoinsDisplayedNumber(coins: Int) {
        COINS_DISPLAYED.put(coins)
    }

}