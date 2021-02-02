package com.nbs.didcard.ui.idmanager

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.didcard.ui.idcard.CreateIDCardActivity
import com.nbs.didcard.ui.idcard.ShowIDCardActivity

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class IDCardManagerViewModel : BaseViewModel() {

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