package com.didchain.didcard.ui.idmanager

import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.bean.CardBean
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.idcard.CreateIDCardActivity
import com.didchain.didcard.ui.idcard.ShowIDCardActivity
import com.didchain.didcard.utils.BitmapUtils
import com.didchain.didcard.utils.CardUtils
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
class IDCardManagerViewModel : BaseViewModel() {
    val exportSuccessEvent: SingleLiveEvent<Boolean> by lazy {
        SingleLiveEvent<Boolean>()
    }

    val clickNewIdCard = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(CreateIDCardActivity::class.java)
        }
    })

    val clickImport = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })

    val clickExport = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showDialog()
            saveIDCard()
        }
    })

    private fun saveIDCard() {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                val cardPath = CardUtils.getCardPath(context())
                val cardBean = CardUtils.loadCardBeanByPath(cardPath)
                val qrJson: String
                if (cardBean != null) {
                    qrJson = JsonUtils.object2Json(cardBean, CardBean::class.java)
                } else {
                    showToast(R.string.id_card_load_error)
                    return@withContext
                }
                val isSave = BitmapUtils.saveBitmapToAlbum(
                    context(),
                    BitmapUtils.stringToQRBitmap(qrJson),
                    context().getString(R.string.app_name)
                )
                if (isSave) {
                    exportSuccessEvent.postValue(true)
                } else {
                    showToast(R.string.save_account_failure)
                }
                dismissDialog()
            }
        }
    }

    val clickUpdatePassword = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(UpdatePasswordActivity::class.java)
        }
    })

    val clickShowIdCard = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(ShowIDCardActivity::class.java)
        }
    })
}