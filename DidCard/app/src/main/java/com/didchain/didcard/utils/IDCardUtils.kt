package com.didchain.didcard.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.didchain.didcard.Constants
import com.didchain.didcard.bean.CardBean
import com.didchain.didcard.provider.context
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object IDCardUtils {
    val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    fun getIDCardPath(context: Context): String {
        return context.filesDir.absolutePath + "/wallet.json"
    }

    fun saveIDCard(path: String, data: String) {
        val file = File(path)
        if(file.exists()){
            file.delete()
        }
        MainScope().launch {
            withContext(Dispatchers.IO) {
                val encryptedFile = getEncryptedFile(path)
                try {
                    encryptedFile.openFileOutput().apply {
                        write(data.toByteArray(Charset.forName("UTF-8")))
                        flush()
                        close()
                    }
                } catch (e: IOException) {
                    Logger.d(e.message)
                }

            }
        }

    }

    suspend fun getId(context: Context): String? {
        val accountPath = getIDCardPath(context)
        return loadIDCardBeanByPath(accountPath)?.did
    }

    suspend fun loadIDCardBeanByPath(path: String): CardBean? {
        return withContext(Dispatchers.IO) {
            println("~~~~~~~~~~~"+Thread.currentThread().name)
            val accountJson = loadIDCardByPath(path)
            return@withContext JsonUtils.Json2Object(accountJson, CardBean::class.java)
        }

    }

    private fun getEncryptedFile(path: String): EncryptedFile {
        val file = File(path)
        return EncryptedFile.Builder(file, context(), masterKeyAlias, EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build()
    }

    suspend fun loadIDCardByPath(path: String): String {
        return withContext(Dispatchers.IO) {
            val encryptedFile = getEncryptedFile(path)
            return@withContext encryptedFile.openFileInput().bufferedReader().readText()
        }

    }

    fun hasIDCard(path: String): Boolean {
        return File(path).exists()
    }

    fun openAlbum(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, Constants.CODE_OPEN_ALBUM)
    }

    @Throws(Exception::class)
    fun parseQRCodeFile(uri: Uri, cr: ContentResolver): String {
        val bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri))
        return parseQRcodeFromBitmap(bitmap)
    }

    @Throws(Exception::class)
    private fun parseQRcodeFromBitmap(bitmap: Bitmap): String {
        val intArray = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val source: LuminanceSource = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val bb = BinaryBitmap(HybridBinarizer(source))
        val hints: MutableMap<DecodeHintType, Any?> = LinkedHashMap()
        hints[DecodeHintType.PURE_BARCODE] = java.lang.Boolean.TRUE
        val reader: Reader = MultiFormatReader()
        val r = reader.decode(bb, hints)
        return r.text
    }

}