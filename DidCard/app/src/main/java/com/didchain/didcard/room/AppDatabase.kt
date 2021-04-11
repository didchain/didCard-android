package com.didchain.didcard.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@Database(entities = [Account::class], version = 2 ,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "account.db" //数据库名称
                ).allowMainThreadQueries().build()
            }
            return instance as AppDatabase
        }
    }
}