package com.nbs.didcard.ui.idmanager

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityUpdatePasswordBinding

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class UpdatePasswordActivity:BaseActivity<UpdatePasswordViewModel,ActivityUpdatePasswordBinding>() {
    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_update_password

    override fun initView() {
        mViewModel.title.set(getString(R.string.id_card_manager_update_password))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE
    override val mViewModel: UpdatePasswordViewModel
        get() = createViewModel(UpdatePasswordViewModel::class.java)
}