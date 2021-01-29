package com.nbs.didcard.ui.main

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.didcard.ui.scan.ScanActivity

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MainViewModel:BaseViewModel() {
    val clickQR = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(ScanActivity::class.java)
        }
    })
}