package com.didchain.didcard.ui.create

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityCreateAccountBinding
import com.didchain.didcard.ui.privacyauthority.PrivacyAuthorityActivity
import kotlinx.android.synthetic.main.activity_create_account.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class CreateCardActivity : BaseActivity<CreateCardViewModel, ActivityCreateAccountBinding>() {
    private val protocolStart = 9
    private val protocolEnd = 15
    private val policyStart = 16
    private val policyEnd = 22

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_create_account
    override val mViewModel: CreateCardViewModel by viewModel()

    override fun initView() {
        val color = resources.getColor(R.color.blue, null)

//        val spannableString = SpannableString(getString(R.string.use_agreement_privacy_authority))
//        spannableString.setSpan(object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                (widget as TextView).highlightColor = resources.getColor(android.R.color.transparent, null)
//                startActivity(PrivacyAuthorityActivity::class.java)
//            }
//
//            override fun updateDrawState(ds: TextPaint) {
//                ds.color = ds.linkColor
//                ds.isUnderlineText = false
//                ds.clearShadowLayer()
//            }
//        }, protocolStart, protocolEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannableString.setSpan(object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                (widget as TextView).highlightColor = resources.getColor(android.R.color.transparent, null)
//                startActivity(PrivacyAuthorityActivity::class.java)
//            }
//
//            override fun updateDrawState(ds: TextPaint) {
//                ds.color = ds.linkColor
//                ds.isUnderlineText = false
//                ds.clearShadowLayer()
//            }
//        }, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        spannableString.setSpan(ForegroundColorSpan(color), protocolStart, protocolEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannableString.setSpan(ForegroundColorSpan(color), policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        privacyAuthorityTv.text = spannableString
//        privacyAuthorityTv.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun initData() {}

    override fun initObserve() {}

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE


}