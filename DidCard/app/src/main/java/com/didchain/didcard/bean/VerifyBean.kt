package com.didchain.didcard.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@JsonClass(generateAdapter = true)
data class VerifyBean(val content: Content, @Json(name = "ext_data") val extData: ExtData, val sig: String

)

@JsonClass(generateAdapter = true)
data class Content(val auth_url: String, val random_token: String, val did: String)

@JsonClass(generateAdapter = true)
data class ExtData(@Json(name = "user_name") val uerName: String, val password: String)
