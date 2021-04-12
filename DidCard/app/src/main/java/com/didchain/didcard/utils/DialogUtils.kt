package com.didchain.didcard.utils

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.didchain.didcard.R
import com.didchain.didcard.ui.privacyauthority.PrivacyAuthorityActivity
import com.didchain.didcard.view.ExportSuccessPop
import com.didchain.didcard.view.ImportSuccessPop
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.OnSelectListener
import com.lxj.xpopup.interfaces.SimpleCallback
import com.orhanobut.logger.Logger

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
object DialogUtils {
    const val POSITION_ALBUM = 0
    const val POSITION_CAMERA = 1

    internal class IDCardXPopupListener : SimpleCallback() {
        override fun onCreated(pv: BasePopupView) {
            Logger.d("tag", "弹窗创建了")
        }

        override fun onShow(popupView: BasePopupView) {
            Logger.d("tag", "onShow")
        }

        override fun onDismiss(popupView: BasePopupView) {
            Logger.d("tag", "onDismiss")
        }

        override fun beforeDismiss(popupView: BasePopupView) {
            Logger.d("tag", "准备消失的时候执行")
        }

        //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
        override fun onBackPressed(popupView: BasePopupView): Boolean {
            return false
        }

        override fun onKeyBoardStateChanged(popupView: BasePopupView, height: Int) {
            super.onKeyBoardStateChanged(popupView, height)
            Logger.d("tag", "onKeyBoardStateChanged height: $height")
        }
    }

    fun showImportDialog(activity: AppCompatActivity, selectListener: OnSelectListener) {
        XPopup.Builder(activity).asBottomList(activity.getString(R.string.guide_dialog_title), arrayOf(activity.getString(R.string.guide_import_album), activity.getString(R.string.guide_import_camera), activity.getString(R.string.cancel)), selectListener).show()
    }

    fun showPasswordDialog(activity: AppCompatActivity, listener: PasswordPop.InputPasswordListener, xpopListener: SimpleCallback = IDCardXPopupListener()): BasePopupView {
        return XPopup.Builder(activity).dismissOnTouchOutside(false).dismissOnBackPressed(true).setPopupCallback(xpopListener).isDestroyOnDismiss(true).asCustom(PasswordPop(activity, listener)).show()

    }


    fun showStartFingerPrintsDialog(activity: AppCompatActivity, confirmListerer: OnConfirmListener, cancelListener: OnCancelListener): BasePopupView {
        return XPopup.Builder(activity).dismissOnTouchOutside(false).dismissOnBackPressed(false).isDestroyOnDismiss(true).asConfirm("", activity.getString(R.string.my_no_fingerprint), activity.getString(R.string.cancel), activity.getString(R.string.my_input), confirmListerer, cancelListener, false).show()

    }

    fun showExportSuccessDialog(context: Context) {
        val exportSuccessPop = XPopup.Builder(context).isDestroyOnDismiss(true).asCustom(ExportSuccessPop(context)).show()
        exportSuccessPop.delayDismiss(1000)
    }

    fun showImportSuccessDialog(context: Context) {
        val importSuccessPop = XPopup.Builder(context).isDestroyOnDismiss(true).asCustom(ImportSuccessPop(context)).show()
        importSuccessPop.delayDismiss(1000)
    }

    fun showPrivacyAuthorityDialog(context: Context, confirmListener: OnConfirmListener, cancelListener: OnCancelListener) {

        val protocolStart = 122
        val protocolEnd = 128
        val policyStart = 129
        val policyEnd = 135

        val spannableString = SpannableString(context.getString(R.string.dialog_service_and_privacy_policy_content))
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                (widget as TextView).highlightColor = context.resources.getColor(android.R.color.transparent, null)
                context.startActivity(Intent(context, PrivacyAuthorityActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = false
                ds.clearShadowLayer()
            }
        }, protocolStart, protocolEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                (widget as TextView).highlightColor = context.resources.getColor(android.R.color.transparent, null)
                context.startActivity(Intent(context, PrivacyAuthorityActivity::class.java))
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = false
                ds.clearShadowLayer()
            }
        }, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorAccent, null)), protocolStart, protocolEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.colorAccent, null)), policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        val importSuccessPop = XPopup.Builder(context).isDestroyOnDismiss(true).dismissOnBackPressed(false).dismissOnTouchOutside(false).asConfirm(context.getString(R.string.privacy_policy_title), spannableString, context.getString(R.string.not_use), context.getString(R.string.agree), confirmListener, cancelListener, false)
        importSuccessPop.show()
    }

    fun showDeleteAccountDailog(context: Context, confirmListener: OnConfirmListener) {
        XPopup.Builder(context).asConfirm(context.getString(R.string.tip), context.getString(R.string.authorization_system_delete_account), confirmListener).show()
    }

}