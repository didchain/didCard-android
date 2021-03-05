package com.didchain.didcard.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@JsonClass(generateAdapter = true)
data class SignatureBean(
    val did: String,
    @Json(name = "time_stamp") val timeStamp: Long,
    val latitude: Double,
    val longitude: Double
)