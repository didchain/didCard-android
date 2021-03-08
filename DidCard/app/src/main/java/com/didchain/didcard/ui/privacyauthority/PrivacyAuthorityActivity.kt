package com.didchain.didcard.ui.privacyauthority

import android.os.Bundle
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityPrivacyAuthorityBinding
import kotlinx.android.synthetic.main.activity_privacy_authority.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class PrivacyAuthorityActivity : BaseActivity<PrivacyAuthorityViewModel, ActivityPrivacyAuthorityBinding>() {
    override val mViewModel: PrivacyAuthorityViewModel by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_privacy_authority

    override fun initView() {
        mViewModel.title.set(getString(R.string.privacy_policy_title))
        mViewModel.showBackImage.set(true)
        content.loadUrl("file:///android_asset/privacyauthority.html")
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    override fun initVariableId(): Int = BR.viewModel
}