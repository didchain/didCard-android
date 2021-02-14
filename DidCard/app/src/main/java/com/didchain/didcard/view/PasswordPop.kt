package com.didchain.didcard.view

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.didchain.didcard.R
import com.lxj.xpopup.core.CenterPopupView

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class PasswordPop(context: Context, val listener: OpenListener) : CenterPopupView(context) {
    interface OpenListener {
        fun open(password: String)
    }

    override fun getImplLayoutId(): Int {
        return R.layout.layout_password
    }

    override fun onCreate() {
        super.onCreate()
        val password = findViewById<EditText>(R.id.password)
        findViewById<TextView>(R.id.unlock).setOnClickListener {
            listener.open(password.text.toString())
        }
    }
}