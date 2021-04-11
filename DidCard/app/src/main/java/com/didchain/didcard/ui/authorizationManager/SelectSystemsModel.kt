package com.didchain.didcard.ui.authorizationManager

import com.didchain.android.lib.base.BaseModel
import okhttp3.internal.wait
import rxhttp.toList
import rxhttp.toOkResponse
import rxhttp.toParser
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class SelectSystemsModel:BaseModel() {

    suspend fun getSystems(): List<String> {
        return RxHttp.get("/api/hostList").toResponse<List<String>>().await()
    }
}