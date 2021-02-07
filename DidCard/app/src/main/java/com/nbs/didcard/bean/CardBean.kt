package com.nbs.didcard.bean

import com.squareup.moshi.JsonClass

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@JsonClass(generateAdapter = true)
data class CardBean(var version: Int = 0, var did: String = "", var cipher_txt: String = "")