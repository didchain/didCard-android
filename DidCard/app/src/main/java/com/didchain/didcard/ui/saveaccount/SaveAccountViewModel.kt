package com.didchain.didcard.ui.saveaccount

import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.main.MainActivity
import com.didchain.didcard.utils.BitmapUtils

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class SaveAccountViewModel : BaseViewModel() {
    val saveAlbumEvent = SingleLiveEvent<Any>()
    val saveAlbumResultEvent = SingleLiveEvent<Boolean>()
    val clickSaveAlbum = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showToast("备份到相册")
            saveAlbumEvent.call()
            //            startActivity(MainActivity::class.java)
            //            finish()
        }
    })

    val clickSkip = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivityAndFinish(MainActivity::class.java)
        }
    })

    fun saveCard2Album(data: String) {
        val isSave = BitmapUtils.saveBitmapToAlbum(
            context(),
            BitmapUtils.stringToQRBitmap(data),
            context().getString(R.string.app_name)
        )
        if (isSave) {
            saveAlbumResultEvent.postValue(true)
        } else {
            saveAlbumResultEvent.postValue(false)
        }
    }
}