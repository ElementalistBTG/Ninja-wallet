package com.elementalist.ninjawallet.data.remote.dto.CoinDetails

data class Links(
    val announcement_url: List<String>,
    val bitcointalk_thread_identifier: Any,
    val blockchain_site: List<String>,
    val chat_url: List<String>,
    val facebook_username: String,
    val homepage: List<String>,
    val official_forum_url: List<String>,
    val repos_url: ReposUrl,
    val subreddit_url: String,
    val telegram_channel_identifier: String,
    val twitter_screen_name: String
)