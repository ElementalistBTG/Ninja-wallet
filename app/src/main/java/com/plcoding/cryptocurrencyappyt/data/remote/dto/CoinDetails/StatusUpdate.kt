package com.plcoding.cryptocurrencyappyt.data.remote.dto.CoinDetails

data class StatusUpdate(
    val category: String,
    val created_at: String,
    val description: String,
    val pin: Boolean,
    val project: Project,
    val user: String,
    val user_title: String
)