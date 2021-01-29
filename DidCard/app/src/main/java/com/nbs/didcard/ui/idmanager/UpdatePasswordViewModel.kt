package com.nbs.didcard.ui.idmanager

import android.text.TextUtils
import androidx.databinding.ObservableField
import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.didcard.R

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class UpdatePasswordViewModel : BaseViewModel() {
    val oldPassword = ObservableField<String>()
    val newPassword = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    val clickSure = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            if (checkPassword()) {
                showToast("点击了确认")
            }
        }
    })

    private fun checkPassword(): Boolean {
        if (TextUtils.isEmpty(oldPassword.get())) {
            showToast(R.string.update_password_input_old_password)
            return false
        }

        if (TextUtils.isEmpty(newPassword.get())) {
            showToast(R.string.update_password_input_new_password)
            return false
        }

        if (TextUtils.isEmpty(confirmPassword.get())) {
            showToast(R.string.update_password_input_confirm_password)
            return false
        }

        if (newPassword.get() != confirmPassword.get()) {
            showToast(R.string.password_different)
            return false
        }

        return true
    }
}