package com.didchain.didcard.ui.idmanager

import android.os.Bundle
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityUpdatePasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class UpdatePasswordActivity :
        BaseActivity<UpdatePasswordViewModel, ActivityUpdatePasswordBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_update_password
    override val mViewModel: UpdatePasswordViewModel by viewModel()
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

}