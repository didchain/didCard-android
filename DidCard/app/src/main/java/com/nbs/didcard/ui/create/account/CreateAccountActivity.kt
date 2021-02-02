package com.nbs.didcard.ui.create.account

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityCreateAccountBinding
import org.koin.android.ext.android.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class CreateAccountActivity : BaseActivity<CreateAccountViewModel, ActivityCreateAccountBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_create_account
    override val mViewModel: CreateAccountViewModel by inject()

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE


}