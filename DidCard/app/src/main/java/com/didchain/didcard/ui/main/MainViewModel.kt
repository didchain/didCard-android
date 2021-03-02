package com.didchain.didcard.ui.main

import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MainViewModel : BaseViewModel() {
    val openCameraEvent = SingleLiveEvent<Boolean>()
    val clickQR = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            openCameraEvent.call()
        }
    })
}