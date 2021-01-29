package com.nbs.didcard.ui.idcard

import androidx.databinding.ObservableField
import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ShowIDCardViewModel : BaseViewModel() {
    val idCard = ObservableField<String>()
    val clickShare = BindingCommand<Any>(object : BindingAction {
        override fun call() {
                showToast("点击了分享")
        }
    })

    val clickSave= BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showToast("点击了保存")
        }
    })
}