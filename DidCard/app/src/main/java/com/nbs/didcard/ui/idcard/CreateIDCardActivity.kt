package com.nbs.didcard.ui.idcard

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityCreateIdCardBinding
import com.nbs.didcard.ui.create.account.CreateCardViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class CreateIDCardActivity : BaseActivity<CreateCardViewModel, ActivityCreateIdCardBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_create_id_card
    override val mViewModel: CreateCardViewModel by viewModel()

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

}