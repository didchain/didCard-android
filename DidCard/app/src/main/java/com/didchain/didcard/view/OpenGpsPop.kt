package com.didchain.didcard.view

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import androidgolib.Androidgolib
import com.lxj.xpopup.core.CenterPopupView
import com.didchain.didcard.R
import com.orhanobut.logger.Logger

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class OpenGpsPop(context: Context, val listener: UnlockListener) : CenterPopupView(context) {
    interface UnlockListener {
        fun unlock()
    }

    override fun getImplLayoutId(): Int {
        return R.layout.layout_password
    }

    override fun onCreate() {
        super.onCreate()
        val password = findViewById<EditText>(R.id.password)
        findViewById<TextView>(R.id.unlock).setOnClickListener {
            try {
                Androidgolib.open(password.text.toString())
                listener.unlock()
            } catch (e: Throwable) {
                Logger.e(e.message.toString())
            }
            dismiss()
        }
    }
}