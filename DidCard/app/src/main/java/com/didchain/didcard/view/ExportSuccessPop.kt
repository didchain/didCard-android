package com.didchain.didcard.view

import android.content.Context
import com.didchain.didcard.R
import com.lxj.xpopup.core.CenterPopupView

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ExportSuccessPop(context: Context) : CenterPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.layout_export_success
    }
}