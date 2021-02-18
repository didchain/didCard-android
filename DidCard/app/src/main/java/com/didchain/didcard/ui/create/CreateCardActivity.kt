package com.didchain.didcard.ui.create

import android.os.Bundle
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityCreateAccountBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class CreateCardActivity : BaseActivity<CreateCardViewModel, ActivityCreateAccountBinding>() {

    val createAccountModel: CreateCardModel by inject()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_create_account
    override val mViewModel: CreateCardViewModel by viewModel()

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE


}