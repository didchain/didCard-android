package com.didchain.didcard.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDao {
    //查询user表中所有数据
    @get:Query("SELECT * FROM account")
    val all: List<Account?>?

    @Query("SELECT * FROM account where url = :url")
    fun queryByUrl(url:String): List<Account>?

    @Insert
    fun insert(account: Account)

    @Update
    fun updateAccounts(vararg account: Account)


}