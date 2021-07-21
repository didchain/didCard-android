package com.didchain.didcard.ui.authorizationManager

import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.command.BindingConsumer
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.room.Account
import com.didchain.didcard.room.DataBaseManager

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
    var finishResultActivityEvent = SingleLiveEvent<String>()

    val showPasswordEvent = SingleLiveEvent<Boolean>()
    val openSelectSystemEvent = SingleLiveEvent<Any>()
    val clickDeleteEvent = SingleLiveEvent<Any>()
    val clickSure = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            when {
                TextUtils.isEmpty(url.get()) -> showToast(R.string.authorization_system_please_choice_system)
                TextUtils.isEmpty(userName.get()) -> showToast(R.string.authorization_system_please_input_user_name)
                TextUtils.isEmpty(password.get()) -> showToast(R.string.authorization_system_please_input_password)
                else -> {
                    if (oldAccount != null) {
                        updateAccount()
                    } else {
                        addAccount()
                    }

                }
            }
        }
    })

    private fun addAccount() {
        rxLifeScope.launch {
            val accounts = DataBaseManager.queryByUrl(url.get()!!)
            var isUsed = false
            if (accounts == null || accounts.isEmpty()) {
                isUsed = true
            }
            DataBaseManager.insert(Account(url.get()!!, userName.get()!!, password.get()!!, isUsed))
            finishResultActivityEvent.call()
        }

    }

    private fun updateAccount() {
        if (oldAccount!!.isUsed) {
            rxLifeScope.launch {
                val accounts = DataBaseManager.queryByUrl(url.get()!!)
                if (accounts != null) {
                    accounts.forEach { it.isUsed = false }
                    DataBaseManager.updateAccounts(*accounts.toTypedArray())
                }
            }
        }

        val account = Account(url.get()!!, userName.get()!!, password.get()!!, oldAccount!!.isUsed)
        account.id = oldAccount!!.id
        rxLifeScope.launch {
            DataBaseManager.updateAccounts(account)
            finishResultActivityEvent.call()
        }

    }

    override fun clickRightTv() {
        super.clickRightTv()
        clickDeleteEvent.call()

    }

    val clickChoiceSystem = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            openSelectSystemEvent.call()
        }
    })

    val clickShowPassword = BindingCommand<Boolean>(null,object : BindingConsumer<Boolean> {
        override fun call(t: Boolean) {
            showPasswordEvent.postValue(t)
        }
    })
}