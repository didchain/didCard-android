package com.didchain.didcard.ui.idcard

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
import com.didchain.didcard.utils.CardUtils
import com.didchain.didcard.utils.JsonUtils
import com.orhanobut.logger.Logger
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
    val clickShare = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showToast("点击了分享")
        }
    })

    val clickSave = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            saveIDCard()
        }
    })

    private fun saveIDCard() {
        if (!TextUtils.isEmpty(qrJson)) {
            MainScope().launch {
                withContext(Dispatchers.IO) {
                    Logger.d("~~~~~~~~~~~~~~~~~~~~~~~" + Thread.currentThread().name)
                    val isSave = BitmapUtils.saveBitmapToAlbum(
                        context(),
                        BitmapUtils.stringToQRBitmap(qrJson),
                        context().getString(R.string.app_name)
                    )
                    if (isSave) {
                        showToast(R.string.save_account_success)
                    } else {
                        showToast(R.string.save_account_failure)
                    }
                }
            }


        }
    }

    init {
        MainScope().launch {
            idCard.set(CardUtils.getId(context()))
            val cardPath = CardUtils.getCardPath(context())
            val cardBean = CardUtils.loadCardBeanByPath(cardPath)
            if (cardBean != null) {
                qrJson = JsonUtils.object2Json(cardBean, CardBean::class.java)
                idCardJsonEvent.postValue(qrJson)
            } else {
                showToast(R.string.id_card_load_error)
            }

        }
    }
}