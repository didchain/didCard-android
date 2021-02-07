package com.nbs.didcard.view

import android.content.Context
import com.lxj.xpopup.core.CenterPopupView
import com.nbs.didcard.R

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ImportSuccessPop(context: Context) : CenterPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.layout_import_success
    }
}