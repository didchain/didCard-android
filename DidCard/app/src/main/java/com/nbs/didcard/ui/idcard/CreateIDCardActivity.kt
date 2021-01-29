package com.nbs.didcard.ui.idcard

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityCreateIdCardBinding
import com.nbs.didcard.ui.create.account.CreateAccountViewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class CreateIDCardActivity:BaseActivity<CreateAccountViewModel,ActivityCreateIdCardBinding>() {
    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_create_id_card

    override fun initView() {
        mViewModel.title.set(getString(R.string.id_card_manager_new))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    override fun initVariableId(): Int = BR.viewModel
    override val mViewModel: CreateAccountViewModel
        get() = createViewModel(CreateAccountViewModel::class.java)
}