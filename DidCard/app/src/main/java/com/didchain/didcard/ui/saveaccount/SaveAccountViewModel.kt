package com.didchain.didcard.ui.saveaccount

import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.event.EventLoadIDCard
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.main.MainActivity
import com.didchain.didcard.utils.BitmapUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SaveAccountViewModel : BaseViewModel() {
    val saveAlbumEvent = SingleLiveEvent<Any>()
    val saveAlbumResultEvent = SingleLiveEvent<Boolean>()
    val clickSaveAlbum = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            saveAlbumEvent.call()
            EventBus.getDefault().post(EventLoadIDCard())
        }
    })

    val clickSkip = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            EventBus.getDefault().post(EventLoadIDCard())
            startActivityAndFinish(MainActivity::class.java)
        }
    })

    fun saveCard2Album(data: String) {
        rxLifeScope.launch {
            withContext(Dispatchers.IO) {
                val isSave = BitmapUtils.saveBitmapToAlbum(context(), BitmapUtils.stringToQRBitmap(data), context().getString(R.string.qr_name))
                if (isSave) {
                    saveAlbumResultEvent.postValue(true)
                } else {
                    saveAlbumResultEvent.postValue(false)
                }
            }
        }
    }
}