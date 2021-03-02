package com.didchain.didcard.view

import android.content.Context
import com.didchain.didcard.R
import com.lxj.xpopup.core.CenterPopupView

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ImportSuccessPop(context: Context) : CenterPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_import_success
    }
}