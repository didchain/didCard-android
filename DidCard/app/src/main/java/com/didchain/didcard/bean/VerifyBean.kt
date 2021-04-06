package com.didchain.didcard.bean

import com.squareup.moshi.JsonClass

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@JsonClass(generateAdapter = true)
data class VerifyBean(
    val content: Content,
    val sig: String

)
@JsonClass(generateAdapter = true)
data class Content(
    val auth_url: String,
    val random_token: String,
    val did: String
)
