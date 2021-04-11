package com.didchain.didcard.ui.authorizationManager

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.provider.context
import com.didchain.didcard.room.Account
import com.didchain.didcard.room.AccountDao
import com.didchain.didcard.room.AppDatabase

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class EditSystemInfoViewModel : BaseViewModel() {

    val url = ObservableField<String>()
    val userName = ObservableField<String>()
    val password = ObservableField<String>()
    var oldAccount: Account? = null
    val accountDao: AccountDao by lazy { AppDatabase.getInstance(context()).accountDao() }

    var finishResultActivityEvent = SingleLiveEvent<String>()
    val openSelectSystemEvent = SingleLiveEvent<Any>()
    val clickSure = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            when {
                TextUtils.isEmpty(url.get()) -> showToast(R.string.authorization_system_please_choice_system)
                TextUtils.isEmpty(userName.get()) -> showToast(R.string.authorization_system_please_input_user_name)
                TextUtils.isEmpty(password.get()) -> showToast(R.string.authorization_system_please_input_password)
                else -> {
                    if (oldAccount != null) {

                        if(oldAccount!!.isUsed){
                            val accounts = accountDao.queryByUrl(url.get()!!)
                            if(accounts!=null){
                                accounts.forEach { it.isUsed = false }
                                accountDao.updateAccounts(*accounts.toTypedArray())
                            }
                        }

                      val account =  Account(url.get()!!, userName.get()!!, password.get()!!, oldAccount!!.isUsed)
                        account.id = oldAccount!!.id
                        accountDao.updateAccounts(account)
                    } else {
                        val accounts = accountDao.queryByUrl(url.get()!!)
                        var isUsed =false
                        if(accounts==null || accounts.isEmpty()){
                            isUsed=true
                        }
                        accountDao.insert(
                            Account(
                                url.get()!!,
                                userName.get()!!,
                                password.get()!!,
                                isUsed
                            )
                        )
                    }
                    finishResultActivityEvent.call()
                }
            }
        }
    })

    val clickChoiceSystem = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            openSelectSystemEvent.call()
        }
    })
}