package com.nbs.didcard.ui.create.account

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.android.lib.utils.toast
import com.nbs.didcard.R
import com.nbs.didcard.provider.context
import com.nbs.didcard.ui.saveaccount.SaveAccountActivity
import com.nbs.didcard.utils.SharedPref

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class
CreateAccountViewModel : BaseViewModel() {
    val password = ObservableField<String>("")
    var name: String by SharedPref(context(), "name", "haha")
    val confirmPassword = ObservableField<String>("")
    val clickCreate = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (verifyPassword()) {
                showDialog(R.string.loading)
                toast("点击了创建按钮")
//                name = "xixixi"
                startActivityAndFinish(SaveAccountActivity::class.java)
//                dismissDialog()
            }

        }
    })

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