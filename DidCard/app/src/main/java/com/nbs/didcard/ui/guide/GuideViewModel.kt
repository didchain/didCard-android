package com.nbs.didcard.ui.guide

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.android.lib.utils.toast
import com.nbs.didcard.ui.create.account.CreateAccountActivity

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class GuideViewModel : BaseViewModel() {

    val clickCreate = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            toast("点击了创建按钮")
            startActivityAndFinish(CreateAccountActivity::class.java)
        }
    })
    val clickImport = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            toast("点击了导入按钮按钮")
        }
    })

}