package com.didchain.didcard.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@Entity(tableName = "account")
@Parcelize
class Account(var url: String, @ColumnInfo(name = "user_name") var userName: String, var password: String, var isUsed: Boolean, @PrimaryKey(autoGenerate = true) var id: Long = 0) : Parcelable