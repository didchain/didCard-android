package com.didchain.didcard.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.didchain.didcard.Constants
import com.didchain.didcard.bean.CardBean
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.Reader
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

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