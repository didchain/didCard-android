package com.didchain.didcard.ui.authorizationManager

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.room.Account
import com.didchain.didcard.room.DataBaseManager
import me.tatarka.bindingcollectionadapter2.ItemBinding
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class AuthorizationSystemViewModel : BaseViewModel() {

    val editAccountEvent = SingleLiveEvent<Account>()
    val addSystemEvent = SingleLiveEvent<Any>()
    val items: ObservableList<AuthorizationSystemItemViewModel> = ObservableArrayList()
    val itemBinding = ItemBinding.of<AuthorizationSystemItemViewModel>(BR.item, R.layout.item_authorization_system)

    init {
        getData()
    }

    fun getData() {
        rxLifeScope.launch {
            val accounts = DataBaseManager.all()
            accounts?.let {
                items.clear()
                accounts.forEach {
                    items.add(AuthorizationSystemItemViewModel(this@AuthorizationSystemViewModel, it!!))
                }
            }
        }

    }

    fun clickEdit(account: Account) {
        editAccountEvent.postValue(account)

    }

    fun updateUseAccount(account: Account) {
        rxLifeScope.launch {
            val accounts = DataBaseManager.queryByUrl(account.url)
            if (accounts != null) {
                accounts.forEach { it.isUsed = false }
                DataBaseManager.updateAccounts(*accounts.toTypedArray())
            }
            account.isUsed = true
            DataBaseManager.updateAccounts(account)
            getData()
        }

    }

    val clickAdd = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            addSystemEvent.call()

        }
    })
}