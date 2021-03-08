package com.didchain.didcard.ui.idcard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import androidx.databinding.ObservableField
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.bean.CardBean
import com.didchain.didcard.provider.context
import com.didchain.didcard.utils.BitmapUtils
import com.didchain.didcard.utils.IDCardUtils
import com.didchain.didcard.utils.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ShowIDCardViewModel : BaseViewModel() {
    var qrJson = ""
    val idCard = ObservableField<String>()
    val idCardJsonEvent = SingleLiveEvent<String>()
    val requestLocalPermissionEvent = SingleLiveEvent<Boolean>()
    val clickShare = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            copyToMemory(context(),idCard.get().toString())
        }
    })

    val clickSave = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            requestLocalPermissionEvent.call()
        }
    })


     fun saveIDCard() {
        if (!TextUtils.isEmpty(qrJson)) {
            MainScope().launch {
                withContext(Dispatchers.IO) {
                    val isSave = BitmapUtils.saveBitmapToAlbum(context(), BitmapUtils.stringToQRBitmap(qrJson), context().getString(R.string.qr_name))
                    if (isSave) {
                        showToast(R.string.save_account_success)
                    } else {
                        showToast(R.string.save_account_failure)
                    }
                }
            }


        }
    }
    fun copyToMemory(context: Context, data: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("pirate memory string", data)
        clipboard.setPrimaryClip(clip)
        showToast(R.string.id_card_copy_success)
    }

    init {
        MainScope().launch {
            idCard.set(IDCardUtils.getId(context()))
            val cardPath = IDCardUtils.getIDCardPath(context())
            val cardBean = IDCardUtils.loadIDCardBeanByPath(cardPath)
            if (cardBean != null) {
                qrJson = JsonUtils.object2Json(cardBean, CardBean::class.java)
                idCardJsonEvent.postValue(qrJson)
            } else {
                showToast(R.string.id_card_load_error)
            }

        }
    }
}