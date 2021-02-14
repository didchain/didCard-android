package com.didchain.didcard.utils

import androidx.appcompat.app.AppCompatActivity
import com.didchain.didcard.R
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnSelectListener

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object DialogUtils {
    const val POSITION_ALBUM = 0
    const val POSITION_CAMERA = 1
    const val POSITION_CANCEL = 2

    fun showImportDialot(activity: AppCompatActivity, selectListener: OnSelectListener) {
        XPopup.Builder(activity).asBottomList(
            activity.getString(R.string.guide_dialog_title), arrayOf(
                activity.getString(R.string.guide_import_album),
                activity.getString(R.string.guide_import_camera),
                activity.getString(R.string.cancel)
            ), selectListener
        ).show()
    }

    fun showPasswordDialog(activity: AppCompatActivity, listener: PasswordPop.OpenListener): BasePopupView {
       return XPopup.Builder(activity).isDestroyOnDismiss(true).asCustom(PasswordPop(activity, listener))
            .show()

    }
}