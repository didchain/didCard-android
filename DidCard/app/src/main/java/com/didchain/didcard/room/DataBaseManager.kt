package com.didchain.didcard.room

import com.didchain.didcard.provider.context
import kotlinx.coroutines.*

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object DataBaseManager {
    val accountDao: AccountDao by lazy { AppDatabase.getInstance(context()).accountDao() }

    suspend fun all(): List<Account?>? {
        return withContext(Dispatchers.IO) {
            return@withContext accountDao.all
        }
    }

    suspend fun queryByUrl(url: String): List<Account>? {
        return withContext(Dispatchers.IO) {
            return@withContext accountDao.queryByUrl(url)
        }

    }

    suspend fun insert(account: Account) {
        withContext(Dispatchers.IO) {
            accountDao.insert(account)
        }
    }

    suspend fun updateAccounts(vararg account: Account) {
        withContext(Dispatchers.IO) {
            accountDao.updateAccounts(*account)
        }

    }

    suspend fun delete(vararg account: Account) {
        withContext(Dispatchers.IO) {
            accountDao.delete(*account)
        }
    }
}