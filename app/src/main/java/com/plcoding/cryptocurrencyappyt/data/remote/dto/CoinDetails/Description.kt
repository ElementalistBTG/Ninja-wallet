package com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails

import com.google.gson.annotations.SerializedName

data class Description(
    val ar: String,
    val de: String,
    val en: String,
    val es: String,
    val fr: String,
    val hu: String,
    val id: String,
    val it: String,
    val ja: String,
    val ko: String,
    val nl: String,
    val pl: String,
    val pt: String,
    val ro: String,
    val ru: String,
    val sv: String,
    val th: String,
    val tr: String,
    val vi: String,
    val zh: String,
    @SerializedName("zh-tw")
    val zh_tw: String
)