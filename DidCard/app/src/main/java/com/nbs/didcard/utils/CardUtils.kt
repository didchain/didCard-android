package com.nbs.didcard.utils

import android.content.Context
import com.nbs.didcard.bean.CardBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object CardUtils {

    fun getCardPath(context: Context): String {
        return context.filesDir.absolutePath + "/wallet.json"
    }

    fun saveCard(path: String, data: String) {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                File(path).writeText(data)
            }
        }

    }

    suspend fun getId(context: Context): String? {
        val accountPath = getCardPath(context)
        return loadCardBeanByPath(accountPath)?.did
    }

    suspend fun loadCardBeanByPath(path: String): CardBean? {
        return withContext(Dispatchers.IO) {
            val accountJson = File(path).readText()
            return@withContext JsonUtils.Json2Object(accountJson, CardBean::class.java)
        }

    }

    suspend fun loadCardByPath(path: String): String {
        return withContext(Dispatchers.IO) {
            return@withContext File(path).readText()
        }

    }

    fun hasCard(path: String): Boolean {
        return File(path).exists()
    }
}