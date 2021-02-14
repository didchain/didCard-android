package com.didchain.didcard.ui.idmanager

import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.ui.idcard.CreateIDCardActivity
import com.didchain.didcard.ui.idcard.ShowIDCardActivity

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
            showToast("导出账号")
            exportSuccessEvent.postValue(true)

        }
    })

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