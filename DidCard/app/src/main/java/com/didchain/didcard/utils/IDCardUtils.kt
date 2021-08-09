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
import com.rxlife.coroutine.RxLifeScope
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
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    fun getIDCardPath(context: Context): String {
        return context.filesDir.absolutePath + "/wallet.json"
    }

    fun saveIDCard(path: String, data: String) {
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        RxLifeScope().launch {
            withContext(Dispatchers.IO) {
                val encryptedFile = getEncryptedFile(path)
                saveIDCard(encryptedFile, data)
            }
        }

    }

    private fun saveIDCard(encryptedFile: EncryptedFile, data: String) {
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

    suspend fun getId(context: Context): String? {
        val accountPath = getIDCardPath(context)
        return loadIDCardByPath(accountPath)?.did
    }

    suspend fun loadIDCardByPath(path: String): CardBean? {
        return withContext(Dispatchers.IO) {
            val accountJson = loadIDCardJson(path)
            return@withContext JsonUtils.json2Object(accountJson, CardBean::class.java)
        }

    }


    suspend fun loadIDCardJson(path: String): String {
        return withContext(Dispatchers.IO) {
            return@withContext loadIDCardByFile(getEncryptedFile(path))
        }

    }

    private fun getEncryptedFile(path: String): EncryptedFile {
        val file = File(path)
        return EncryptedFile.Builder(file, context(), masterKeyAlias, EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build()
    }

    private fun loadIDCardByFile(encryptedFile: EncryptedFile): String {
        return encryptedFile.openFileInput().bufferedReader().readText()
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