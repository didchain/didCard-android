package com.didchain.didcard.view

import android.content.Context
import android.os.Build
import android.text.InputType
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView
import com.didchain.android.lib.utils.toast
import com.didchain.didcard.R
import com.didchain.didcard.utils.ModelUtils.isEMUI
import com.lxj.xpopup.core.CenterPopupView


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class PasswordPop(context: Context, val listener: InputPasswordListener) :
    CenterPopupView(context) {
    interface InputPasswordListener {
        fun input(password: String)
    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_password
    }

    override fun onCreate() {
        super.onCreate()
        val password = findViewById<EditText>(R.id.password)
        findViewById<TextView>(R.id.unlock).setOnClickListener {
            if (TextUtils.isEmpty(password.text.toString().trim())) {
                toast(context.getString(R.string.create_account_input_password))
                return@setOnClickListener
            }
            listener.input(password.text.toString())
            if (isEMUI() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL)
                password.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
        }
    }
}