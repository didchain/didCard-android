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
        @Json(name = "time_tamp") val timeStamp: Long,
        val latitude: Double,
        val longitude: Double,
        val did: String
)