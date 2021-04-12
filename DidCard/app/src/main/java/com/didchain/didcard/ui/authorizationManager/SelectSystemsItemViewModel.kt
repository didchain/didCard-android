package com.didchain.didcard.ui.authorizationManager

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.ItemViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.didcard.room.DataBaseManager
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SelectSystemsItemViewModel(VM: SelectSystemsViewModel, var url: String, val currentUrl: String) : ItemViewModel<SelectSystemsViewModel>(VM) {

    val hasUserName = ObservableBoolean(false)
    val isSelected = url == currentUrl

    init {
        rxLifeScope.launch {
            val accounts = DataBaseManager.queryByUrl(url)
            if (accounts != null && accounts.isNotEmpty()) {
                hasUserName.set(true)
            }
        }

    }

    val clickItem = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            VM.setResultOk(url)
        }
    })
}