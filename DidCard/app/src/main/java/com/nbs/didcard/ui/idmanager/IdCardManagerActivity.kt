package com.nbs.didcard.ui.idmanager

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityIdCardManagerBinding
import org.koin.android.ext.android.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class IDCardManagerActivity : BaseActivity<IDCardManagerViewModel, ActivityIdCardManagerBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_id_card_manager
    override val mViewModel: IDCardManagerViewModel by inject()
    override fun initView() {
        mViewModel.title.set(getString(R.string.my_identity_management))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE


}