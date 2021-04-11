package com.didchain.didcard.ui.authorizationManager

import androidx.databinding.ObservableBoolean
import com.didchain.android.lib.base.ItemViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SelectSystemsItemViewModel(VM: SelectSystemsViewModel, var url:String, val currentUrl:String): ItemViewModel<SelectSystemsViewModel>(VM) {

    val hasUserName = ObservableBoolean(false)
    val isSelected = url==currentUrl
    val clickItem = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            VM.setResultOk(url)
        }
    })
}