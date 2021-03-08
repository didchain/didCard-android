package com.didchain.didcard.ui.create

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.command.BindingConsumer
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.android.lib.utils.AppManager
import com.didchain.didcard.R
import com.didchain.didcard.event.EventLoadIDCard
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.saveaccount.SaveAccountActivity
import com.didchain.didcard.utils.IDCardUtils
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class CreateCardViewModel : BaseViewModel(), KoinComponent {
    private val model: CreateCardModel by inject()
    val password = ObservableField<String>("")
    val confirmPassword = ObservableField<String>("")

    var isCheckedPrivacyAuthrrity = true

    val clickCreate = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (!verifyPassword()) {
                return
            }

            if (!isCheckedPrivacyAuthrrity) {
                showToast(R.string.read_privacy_authority)
                return
            }
            showDialog(R.string.loading)
            createAccount()
        }
    })

    val checkPrivacyAuthrrity = BindingCommand<Boolean>(null, object : BindingConsumer<Boolean> {
        override fun call(t: Boolean) {
            isCheckedPrivacyAuthrrity = t
        }


    })

    private fun createAccount() {
        model.createAccount(password.get()!!).subscribe(object : SingleObserver<String> {
            override fun onSuccess(account: String) {
                createAccountSuccess(account)
            }

            override fun onSubscribe(d: Disposable) {
                addSubscribe(d)
            }

            override fun onError(e: Throwable) {
                createAccountFailure(e)
            }
        })
    }

    private fun createAccountSuccess(account: String) {
        Logger.d(account)
        saveIdCard(account)
        dismissDialog()
        startActivityAndFinish(SaveAccountActivity::class.java)
    }

    private fun saveIdCard(account: String) {
        val accountPath = IDCardUtils.getIDCardPath(context())
        IDCardUtils.saveIDCard(accountPath, account)
    }

    private fun createAccountFailure(e: Throwable) {
        Logger.d(e.message)
        dismissDialog()

    }

    private fun verifyPassword(): Boolean {
        if (TextUtils.isEmpty(password.get())) {
            showToast(R.string.create_account_input_password)
            return false
        }

        if (TextUtils.isEmpty(confirmPassword.get())) {
            showToast(R.string.create_account_input_password_again)
            return false
        }

        if (!password.get().equals(confirmPassword.get())) {
            showToast(R.string.password_different)
            return false
        }

        return true
    }
}