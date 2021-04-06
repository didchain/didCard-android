package com.didchain.didcard.ui.main

import androidgolib.Androidgolib
import com.didchain.didcard.bean.Content
import com.didchain.didcard.bean.VerifyBean
import com.didchain.didcard.utils.HttpService
import com.didchain.didcard.utils.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MainModel {

    suspend fun sign(id: String, randomToken: String, authUrl: String): String {
        return withContext(Dispatchers.IO) {
            return@withContext Androidgolib.sign(Androidgolib.signUserLoginMessage(id, randomToken, authUrl))
        }
    }

    @Throws(Exception::class)
    suspend fun verify(id: String, randomToken: String, authUrl: String):String {
        val sig = sign(id, randomToken, authUrl)
        val content = Content(authUrl, randomToken,id)
        val verifyBean = VerifyBean(content, sig)
        val verifyJson = JsonUtils.object2Json(verifyBean, VerifyBean::class.java)
        return withContext(Dispatchers.IO) {
            return@withContext HttpService().sendPostRequest(verifyJson, authUrl)

        }

    }
}