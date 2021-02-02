package com.nbs.didcard.ui.saveaccount

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.android.lib.event.SingleLiveEvent
import com.nbs.didcard.ui.main.MainActivity

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class SaveAccountViewModel : BaseViewModel() {
    val saveAlbumEvent = SingleLiveEvent<Any>()
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

    fun saveAlbum() {
//        BitmapUtils.saveBitmapToAlbum()
    }
}