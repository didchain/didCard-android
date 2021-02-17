package com.didchain.didcard.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class EncryptedPreference(context: Context) {
    val fileName = "id_card_encrypted"
    private val prefs by lazy {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias =
            MasterKey.Builder(context).setKeyGenParameterSpec(keyGenParameterSpec).build()
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            fileName,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return@lazy sharedPreferences
    }

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String = ""): String {
        return prefs.getString(key, defValue)!!
    }
}