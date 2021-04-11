package com.didchain.didcard.ui.authorizationManager

import androidx.databinding.ObservableBoolean
import com.didchain.android.lib.base.ItemViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.didcard.room.Account
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
class AuthorizationSystemItemViewModel(VM: AuthorizationSystemViewModel, var account: Account): ItemViewModel<AuthorizationSystemViewModel>(VM) {


    val clickEdit = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            viewModel.clickEdit(account)
        }
    })

    val clickUsed = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if(!account.isUsed){
                viewModel.updateUseAccount(account)
            }
        }
    })
}