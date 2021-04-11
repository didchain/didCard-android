package com.didchain.didcard.ui.main

import androidgolib.Androidgolib
import com.didchain.didcard.bean.Content
import com.didchain.didcard.bean.ExtData
import com.didchain.didcard.bean.VerifyBean
import com.didchain.didcard.provider.context
import com.didchain.didcard.room.Account
import com.didchain.didcard.room.AccountDao
import com.didchain.didcard.room.AppDatabase
import com.didchain.didcard.utils.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MainModel {
    val accountDao : AccountDao by lazy { AppDatabase.getInstance(context()).accountDao() }

    suspend fun sign(id: String, randomToken: String, authUrl: String): String {
        return withContext(Dispatchers.IO) {
            return@withContext Androidgolib.sign(Androidgolib.signUserLoginMessage(id, randomToken, authUrl))
        }
    }

    @Throws(Exception::class)
    suspend fun verify(id: String, randomToken: String, authUrl: String, currentAccount: Account):String {
        val sig = sign(id, randomToken, authUrl)
        val content = Content(authUrl, randomToken,id)
        val extData = ExtData(currentAccount.userName,currentAccount.password)
        val verifyBean = VerifyBean(content, extData, sig)
        val verifyJson = JsonUtils.object2Json(verifyBean, VerifyBean::class.java)
        return RxHttp.postJson("/api/verify").addAll(verifyJson).toStr().await()

    }
}