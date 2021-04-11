package com.didchain.didcard.ui.authorizationManager

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.provider.context
import com.didchain.didcard.room.Account
import com.didchain.didcard.room.AccountDao
import com.didchain.didcard.room.AppDatabase
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
    private val accountDao: AccountDao by lazy { AppDatabase.getInstance(context()).accountDao() }
    val items: ObservableList<AuthorizationSystemItemViewModel> = ObservableArrayList()
    val itemBinding = ItemBinding.of<AuthorizationSystemItemViewModel>(BR.item, R.layout.item_authorization_system)
    init {
        getData()
    }

    fun getData() {
        val accounts = accountDao.all

        accounts?.let {
            items.clear()
                accounts.forEach {
                    items.add(AuthorizationSystemItemViewModel(this@AuthorizationSystemViewModel,it!!))
                }
        }
    }

    fun clickEdit(account: Account) {
        editAccountEvent.postValue(account)

    }

    fun updateUseAccount(account: Account) {
        val accounts = accountDao.queryByUrl(account.url)
        if(accounts!=null){
            accounts.forEach { it.isUsed = false }
            accountDao.updateAccounts(*accounts.toTypedArray())
        }
        account.isUsed=true
        accountDao.updateAccounts(account)
        getData()
    }

    val clickAdd = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            addSystemEvent.call()

        }
    })
}