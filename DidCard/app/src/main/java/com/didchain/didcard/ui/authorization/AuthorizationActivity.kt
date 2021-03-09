package com.didchain.didcard.ui.authorization

import android.os.Bundle
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityAuthorizationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class AuthorizationActivity : BaseActivity<AuthorizationViewModel, ActivityAuthorizationBinding>() {
    override val mViewModel: AuthorizationViewModel by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_authorization

    override fun initView() {
        mViewModel.title.set(getString(R.string.my_authorization))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {}

    override fun initObserve() {}

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY

    override fun initVariableId(): Int = BR.viewModel

}